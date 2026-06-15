package com.example.ecoquizgame.ui.language

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.core.os.LocaleListCompat
import androidx.navigation.fragment.findNavController
import com.example.ecoquizgame.R
import com.example.ecoquizgame.databinding.FragmentLanguageSelectionBinding
import com.example.ecoquizgame.util.PreferenceManager

class LanguageSelectionFragment : Fragment(R.layout.fragment_language_selection) {
    private var _binding: FragmentLanguageSelectionBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LanguageViewModel by viewModels {
        LanguageViewModelFactory(PreferenceManager(requireContext().applicationContext))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentLanguageSelectionBinding.bind(view)

        binding.buttonEnglish.setOnClickListener {
            viewModel.setLanguage("en")
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
            findNavController().navigate(R.id.action_languageSelectionFragment_to_authFragment)
        }
        binding.buttonBulgarian.setOnClickListener {
            viewModel.setLanguage("bg")
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("bg"))
            findNavController().navigate(R.id.action_languageSelectionFragment_to_authFragment)
        }
        
        animateLogo()
    }

    private fun animateLogo() {
        _binding?.logo?.animate()
            ?.scaleX(1.1f)
            ?.scaleY(1.1f)
            ?.setDuration(2000)
            ?.withEndAction {
                _binding?.logo?.animate()
                    ?.scaleX(1.0f)
                    ?.scaleY(1.0f)
                    ?.setDuration(2000)
                    ?.withEndAction { animateLogo() }
                    ?.start()
            }?.start()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
