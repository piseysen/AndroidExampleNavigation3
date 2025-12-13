package com.pisey.examplenavigation3.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()
    
    fun updateEmail(email: String) {
        _uiState.value = _uiState.value.copy(email = email, error = null)
    }
    
    fun updatePassword(password: String) {
        _uiState.value = _uiState.value.copy(password = password, error = null)
    }
    
    fun login() {
        val currentState = _uiState.value
        
        if (currentState.email.isBlank()) {
            _uiState.value = currentState.copy(error = "Email is required")
            return
        }
        
        if (currentState.password.isBlank()) {
            _uiState.value = currentState.copy(error = "Password is required")
            return
        }
        
        if (!isValidEmail(currentState.email)) {
            _uiState.value = currentState.copy(error = "Please enter a valid email")
            return
        }
        
        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, error = null)
            
            try {
                // Simulate login API call
                kotlinx.coroutines.delay(2000)
                
                // Simple validation - in real app, this would be API call
                if (currentState.email == "test@example.com" && currentState.password == "password") {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        isLoginSuccessful = true
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Invalid email or password"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = "Login failed: ${e.message}"
                )
            }
        }
    }
    
    fun logout() {
        _uiState.value = LoginUiState()
    }
    
    fun clearError() {
        _uiState.value = _uiState.value.copy(error = null)
    }
    
    private fun isValidEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}