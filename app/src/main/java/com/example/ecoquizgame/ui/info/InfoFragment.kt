package com.example.ecoquizgame.ui.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.ecoquizgame.R
import com.example.ecoquizgame.databinding.FragmentInfoBinding

class InfoFragment : Fragment(R.layout.fragment_info) {
    private var _binding: FragmentInfoBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentInfoBinding.bind(view)

        // Basic animation for entry
        binding.root.alpha = 0f
        binding.root.animate().alpha(1f).setDuration(500).start()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
