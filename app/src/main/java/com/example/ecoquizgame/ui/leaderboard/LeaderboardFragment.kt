package com.example.ecoquizgame.ui.leaderboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ecoquizgame.EcoQuizApplication
import com.example.ecoquizgame.R
import com.example.ecoquizgame.databinding.FragmentLeaderboardBinding
import com.example.ecoquizgame.ui.adapter.LeaderboardAdapter
import kotlinx.coroutines.launch

class LeaderboardFragment : Fragment(R.layout.fragment_leaderboard) {
    private var _binding: FragmentLeaderboardBinding? = null
    private val binding get() = _binding!!
    private val adapter = LeaderboardAdapter()

    private val viewModel: LeaderboardViewModel by viewModels {
        val app = requireActivity().application as EcoQuizApplication
        LeaderboardViewModelFactory(app.leaderboardRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLeaderboardBinding.bind(view)

        binding.leaderboardRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.leaderboardRecycler.adapter = adapter
        viewModel.observeTopScores()

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.scores.collect { adapter.submitData(it) }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
