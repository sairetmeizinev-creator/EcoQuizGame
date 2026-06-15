package com.example.ecoquizgame.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.ecoquizgame.data.repository.AuthRepository
import com.example.ecoquizgame.util.PreferenceManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthUiState(
    val isLoading: Boolean = false,
    val message: String = "",
    val isLoggedIn: Boolean = false
)

class AuthViewModel(
    private val authRepository: AuthRepository,
    private val preferenceManager: PreferenceManager
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun signUp(username: String, password: String) {
        if (username.isBlank()) {
            _uiState.value = AuthUiState(message = "Username cannot be empty")
            return
        }
        if (password.length < 6) {
            _uiState.value = AuthUiState(message = "Password must be at least 6 characters")
            return
        }
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            val result = authRepository.signUp(username, password)
            _uiState.value = if (result.isSuccess) {
                AuthUiState(message = "Sign up successful")
            } else {
                AuthUiState(message = result.exceptionOrNull()?.message ?: "Sign up failed")
            }
        }
    }

    fun login(username: String, password: String) {
        if (username.isBlank()) {
            _uiState.value = AuthUiState(message = "Username cannot be empty")
            return
        }
        if (password.length < 6) {
            _uiState.value = AuthUiState(message = "Password must be at least 6 characters")
            return
        }
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            val result = authRepository.login(username, password)
            _uiState.value = if (result.isSuccess) {
                val user = result.getOrThrow()
                preferenceManager.setCurrentUser(user.id, user.username)
                AuthUiState(message = "Login successful", isLoggedIn = true)
            } else {
                AuthUiState(message = result.exceptionOrNull()?.message ?: "Login failed")
            }
        }
    }
}

class AuthViewModelFactory(
    private val authRepository: AuthRepository,
    private val preferenceManager: PreferenceManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(authRepository, preferenceManager) as T
    }
}
