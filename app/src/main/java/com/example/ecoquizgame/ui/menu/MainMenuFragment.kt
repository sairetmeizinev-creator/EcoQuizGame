package com.example.ecoquizgame.ui.menu

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ecoquizgame.R
import com.example.ecoquizgame.databinding.FragmentMainMenuBinding

class MainMenuFragment : Fragment(R.layout.fragment_main_menu) {
    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainMenuBinding.bind(view)

        binding.normalModeButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_normalModeFragment)
        }
        binding.timeModeButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_timeModeFragment)
        }
        binding.multiplayerModeButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_multiplayerLobbyFragment)
        }

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_settingsFragment)
        }
        binding.infoButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_infoFragment)
        }
        binding.leaderboardButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainMenuFragment_to_leaderboardFragment)
        }

        animateDecorations()
    }

    private fun animateDecorations() {
        _binding?.cartoonLeaf1?.animate()
            ?.translationYBy(20f)
            ?.setDuration(2000)
            ?.withEndAction {
                _binding?.cartoonLeaf1?.animate()
                    ?.translationYBy(-20f)
                    ?.setDuration(2000)
                    ?.withEndAction { animateDecorations() }
                    ?.start()
            }?.start()

        _binding?.cartoonLeaf2?.animate()
            ?.rotationBy(10f)
            ?.setDuration(3000)
            ?.withEndAction {
                _binding?.cartoonLeaf2?.animate()
                    ?.rotationBy(-10f)
                    ?.setDuration(3000)
                    ?.withEndAction { animateDecorations() }
                    ?.start()
            }?.start()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
