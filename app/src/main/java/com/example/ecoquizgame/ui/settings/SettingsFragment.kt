package com.example.ecoquizgame.ui.settings

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.ecoquizgame.EcoQuizApplication
import com.example.ecoquizgame.R
import com.example.ecoquizgame.databinding.FragmentSettingsBinding
import com.example.ecoquizgame.ui.leaderboard.LeaderboardViewModel
import com.example.ecoquizgame.ui.leaderboard.LeaderboardViewModelFactory
import com.example.ecoquizgame.util.PreferenceManager
import kotlinx.coroutines.launch

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    private val leaderboardViewModel: LeaderboardViewModel by viewModels {
        val app = requireActivity().application as EcoQuizApplication
        LeaderboardViewModelFactory(app.leaderboardRepository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSettingsBinding.bind(view)

        val pref = PreferenceManager(requireContext().applicationContext)

        binding.soundSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewLifecycleOwner.lifecycleScope.launch { pref.setSoundEnabled(isChecked) }
        }
        binding.languageEnglishButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch { pref.setLanguage("en") }
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
            Toast.makeText(requireContext(), R.string.language_updated, Toast.LENGTH_SHORT).show()
        }
        binding.languageBulgarianButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch { pref.setLanguage("bg") }
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("bg"))
            Toast.makeText(requireContext(), R.string.language_updated, Toast.LENGTH_SHORT).show()
        }
        binding.resetProgressButton.setOnClickListener {
            leaderboardViewModel.clearProgress()
            Toast.makeText(requireContext(), R.string.progress_reset, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
