package com.example.ecoquizgame.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.ecoquizgame.EcoQuizApplication
import com.example.ecoquizgame.R
import com.example.ecoquizgame.databinding.FragmentAuthBinding
import com.example.ecoquizgame.util.PreferenceManager
import kotlinx.coroutines.launch

class AuthFragment : Fragment(R.layout.fragment_auth) {
    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AuthViewModel by viewModels {
        val app = requireActivity().application as EcoQuizApplication
        AuthViewModelFactory(app.authRepository, PreferenceManager(requireContext().applicationContext))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAuthBinding.bind(view)

        binding.loginButton.setOnClickListener {
            viewModel.login(
                binding.usernameEdit.text.toString().trim(),
                binding.passwordEdit.text.toString().trim()
            )
        }
        binding.signupButton.setOnClickListener {
            viewModel.signUp(
                binding.usernameEdit.text.toString().trim(),
                binding.passwordEdit.text.toString().trim()
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                if (state.message.isNotBlank()) Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                if (state.isLoggedIn) findNavController().navigate(R.id.action_authFragment_to_mainMenuFragment)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
