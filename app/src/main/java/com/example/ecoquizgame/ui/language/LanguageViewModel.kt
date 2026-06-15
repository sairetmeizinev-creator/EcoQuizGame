package com.example.ecoquizgame.ui.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ecoquizgame.util.PreferenceManager
import kotlinx.coroutines.launch

class LanguageViewModel(
    private val preferenceManager: PreferenceManager
) : ViewModel() {
    fun setLanguage(code: String) {
        viewModelScope.launch {
            preferenceManager.setLanguage(code)
        }
    }
}

class LanguageViewModelFactory(
    private val preferenceManager: PreferenceManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LanguageViewModel(preferenceManager) as T
    }
}
