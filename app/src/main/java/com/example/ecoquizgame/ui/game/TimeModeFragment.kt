package com.example.ecoquizgame.ui.game

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecoquizgame.EcoQuizApplication
import com.example.ecoquizgame.R
import com.example.ecoquizgame.databinding.FragmentGameBinding
import com.example.ecoquizgame.ui.adapter.AnswerAdapter
import com.example.ecoquizgame.util.PreferenceManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TimeModeFragment : Fragment(R.layout.fragment_game) {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var answerAdapter: AnswerAdapter
    private var gameOverDialogShowing = false

    private val viewModel: GameViewModel by viewModels {
        val app = requireActivity().application as EcoQuizApplication
        GameViewModelFactory(app.gameRepository, app.leaderboardRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentGameBinding.bind(view)

        answerAdapter = AnswerAdapter { index -> viewModel.onAnswerSelected(index) }
        binding.answersRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.answersRecycler.adapter = answerAdapter
        binding.livesText.visibility = View.GONE
        binding.timerText.visibility = View.VISIBLE

        viewLifecycleOwner.lifecycleScope.launch {
            val pref = PreferenceManager(requireContext().applicationContext)
            val lang = pref.languageFlow.first()
            viewModel.start(
                GameMode.TIME,
                pref.currentUserIdFlow.first(),
                pref.currentUsernameFlow.first(),
                lang,
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                state.question?.let {
                    binding.questionText.text = it.text
                    answerAdapter.submitData(it.answers)
                    binding.questionProgress.max = state.totalQuestions
                    binding.questionProgress.setProgress(state.questionIndex + 1, true)
                }
                binding.scoreText.text = getString(R.string.score_format, state.score)
                binding.timerText.text = getString(R.string.timer_format, state.secondsLeft)
                answerAdapter.showWrongAnswers(state.wrongAnswers)
                if (state.isGameOver && !gameOverDialogShowing) {
                    showGameOverDialog(state.score)
                }
            }
        }

        animateDecoration()
    }

    private fun animateDecoration() {
        _binding?.gameLeafDecoration?.animate()
            ?.scaleX(1.1f)
            ?.scaleY(1.1f)
            ?.setDuration(3000)
            ?.withEndAction {
                _binding?.gameLeafDecoration?.animate()
                    ?.scaleX(1.0f)
                    ?.scaleY(1.0f)
                    ?.setDuration(3000)
                    ?.withEndAction { animateDecoration() }
                    ?.start()
            }?.start()
    }

    private fun showGameOverDialog(score: Int) {
        gameOverDialogShowing = true
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.game_over)
            .setMessage(getString(R.string.game_over_message, score))
            .setCancelable(false)
            .setPositiveButton(R.string.play_again) { _, _ ->
                gameOverDialogShowing = false
                restartGame()
            }
            .setNegativeButton(R.string.main_menu) { _, _ ->
                findNavController().navigate(R.id.action_timeModeFragment_to_mainMenuFragment)
            }
            .show()
    }

    private fun restartGame() {
        viewLifecycleOwner.lifecycleScope.launch {
            val pref = PreferenceManager(requireContext().applicationContext)
            val lang = pref.languageFlow.first()
            viewModel.start(
                GameMode.TIME,
                pref.currentUserIdFlow.first(),
                pref.currentUsernameFlow.first(),
                lang,
            )
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
