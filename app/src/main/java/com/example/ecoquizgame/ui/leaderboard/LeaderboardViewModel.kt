package com.example.ecoquizgame.ui.leaderboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ecoquizgame.data.local.entity.ScoreEntity
import com.example.ecoquizgame.data.repository.LeaderboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LeaderboardViewModel(
    private val leaderboardRepository: LeaderboardRepository
) : ViewModel() {
    private val _scores = MutableStateFlow<List<ScoreEntity>>(emptyList())
    val scores: StateFlow<List<ScoreEntity>> = _scores.asStateFlow()

    fun observeTopScores() {
        viewModelScope.launch {
            leaderboardRepository.topScores().collectLatest { _scores.value = it }
        }
    }

    fun clearProgress() {
        viewModelScope.launch {
            leaderboardRepository.clearProgress()
        }
    }
}

class LeaderboardViewModelFactory(
    private val leaderboardRepository: LeaderboardRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LeaderboardViewModel(leaderboardRepository) as T
    }
}
