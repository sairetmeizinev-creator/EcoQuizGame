package com.example.ecoquizgame.ui.multiplayer

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ecoquizgame.R
import com.example.ecoquizgame.databinding.FragmentMultiplayerLobbyBinding

class MultiplayerLobbyFragment : Fragment(R.layout.fragment_multiplayer_lobby) {
    private var _binding: FragmentMultiplayerLobbyBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMultiplayerLobbyBinding.bind(view)

        binding.generateCodeButton.setOnClickListener {
            val code = (1000..9999).random().toString()
            binding.codeInput.setText(code)
            Toast.makeText(requireContext(), "Code generated: $code", Toast.LENGTH_SHORT).show()
        }

        binding.joinButton.setOnClickListener {
            val code = binding.codeInput.text.toString().trim()
            if (code.length < 4) {
                Toast.makeText(requireContext(), R.string.invalid_game_code, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val args = Bundle().apply { putString("code", code) }
            findNavController().navigate(R.id.action_multiplayerLobbyFragment_to_multiplayerGameFragment, args)
        }

        animateLobby()
    }

    private fun animateLobby() {
        _binding?.multiplayerIcon?.animate()
            ?.scaleX(1.1f)
            ?.scaleY(1.1f)
            ?.setDuration(2500)
            ?.withEndAction {
                _binding?.multiplayerIcon?.animate()
                    ?.scaleX(1.0f)
                    ?.scaleY(1.0f)
                    ?.setDuration(2500)
                    ?.withEndAction { animateLobby() }
                    ?.start()
            }?.start()
            
        _binding?.lobbyLeaf1?.animate()
            ?.rotationBy(20f)
            ?.setDuration(4000)
            ?.withEndAction {
                _binding?.lobbyLeaf1?.animate()
                    ?.rotationBy(-20f)
                    ?.setDuration(4000)
                    ?.withEndAction { animateLobby() }
                    ?.start()
            }?.start()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
