package com.example.ecoquizgame.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ecoquizgame.data.local.entity.ScoreEntity
import com.example.ecoquizgame.data.model.Question
import com.example.ecoquizgame.data.repository.GameRepository
import com.example.ecoquizgame.data.repository.LeaderboardRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class GameMode { NORMAL, TIME, MULTIPLAYER }

data class GameState(
    val question: Question? = null,
    val questionIndex: Int = 0,
    val totalQuestions: Int = 0,
    val score: Int = 0,
    val lives: Int = 3,
    val secondsLeft: Int = 60,
    val wrongAnswers: Set<Int> = emptySet(),
    val isGameOver: Boolean = false,
    val winnerText: String = "",
)

class GameViewModel(
    private val gameRepository: GameRepository,
    private val leaderboardRepository: LeaderboardRepository,
) : ViewModel() {
    private var questions = emptyList<Question>()
    private var mode: GameMode = GameMode.NORMAL
    private var userId: Long = -1
    private var username: String = ""

    private val _uiState = MutableStateFlow(GameState())
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    fun start(mode: GameMode, userId: Long, username: String, locale: String = "en") {
        this.mode = mode
        this.userId = userId
        this.username = username
        this.questions = gameRepository.getQuestions(locale).shuffled()

        _uiState.value = GameState(
            question = questions.firstOrNull(),
            totalQuestions = questions.size,
            lives = if (mode == GameMode.NORMAL) 3 else 0,
            secondsLeft = if (mode == GameMode.NORMAL) 0 else 60
        )
        if (mode != GameMode.NORMAL) startTimer()
    }

    fun onAnswerSelected(answerIndex: Int) {
        val current = _uiState.value
        val question = current.question ?: return
        val isCorrect = question.correctAnswers.contains(answerIndex)

        if (isCorrect) {
            moveToNextQuestion(current.score + 1)
        } else {
            when (mode) {
                GameMode.NORMAL -> {
                    val newLives = current.lives - 1
                    _uiState.value = current.copy(
                        lives = newLives,
                        wrongAnswers = current.wrongAnswers + answerIndex,
                        isGameOver = newLives <= 0
                    )
                    if (newLives <= 0) finishGame()
                }
                GameMode.TIME -> {
                    _uiState.value = current.copy(
                        secondsLeft = (current.secondsLeft - 3).coerceAtLeast(0),
                        wrongAnswers = current.wrongAnswers + answerIndex
                    )
                    if (_uiState.value.secondsLeft <= 0) finishGame()
                }
                GameMode.MULTIPLAYER -> {
                    _uiState.value = current.copy(
                        score = current.score - 1,
                        wrongAnswers = current.wrongAnswers + answerIndex
                    )
                }
            }
        }
    }

    private fun moveToNextQuestion(newScore: Int) {
        val nextIndex = (_uiState.value.questionIndex + 1) % questions.size
        _uiState.value = _uiState.value.copy(
            questionIndex = nextIndex,
            question = questions[nextIndex],
            score = newScore,
            wrongAnswers = emptySet()
        )
    }

    private fun startTimer() {
        viewModelScope.launch {
            while ((_uiState.value.secondsLeft > 0) && !_uiState.value.isGameOver) {
                delay(1000)
                _uiState.value = _uiState.value.copy(secondsLeft = _uiState.value.secondsLeft - 1)
            }
            if (!_uiState.value.isGameOver) finishGame()
        }
    }

    fun finishGame(multiplayerEnemyScore: Int? = null) {
        val winner = if ((mode == GameMode.MULTIPLAYER) && (multiplayerEnemyScore != null)) {
            when {
                _uiState.value.score > multiplayerEnemyScore -> "You win!"
                _uiState.value.score < multiplayerEnemyScore -> "You lose!"
                else -> "Draw!"
            }
        } else {
            ""
        }

        _uiState.value = _uiState.value.copy(isGameOver = true, winnerText = winner)
        viewModelScope.launch {
            leaderboardRepository.saveScore(
                ScoreEntity(
                    userId = userId,
                    username = username,
                    value = _uiState.value.score,
                    mode = mode.name,
                    timestamp = System.currentTimeMillis()
                )
            )
        }
    }
}

class GameViewModelFactory(
    private val gameRepository: GameRepository,
    private val leaderboardRepository: LeaderboardRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return GameViewModel(gameRepository, leaderboardRepository) as T
    }
}
