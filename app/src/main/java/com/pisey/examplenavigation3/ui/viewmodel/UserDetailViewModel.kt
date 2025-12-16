package com.pisey.examplenavigation3.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pisey.examplenavigation3.data.model.User
import com.pisey.examplenavigation3.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserDetailUiState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val error: String? = null,
    val isEditing: Boolean = false
)

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private var userId: String = ""
    
    private val _uiState = MutableStateFlow(UserDetailUiState())
    val uiState: StateFlow<UserDetailUiState> = _uiState.asStateFlow()
    
    fun initialize(userId: String) {
        this.userId = userId
        loadUser()
    }
    
    private fun loadUser() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val user = userRepository.getUserById(userId)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    user = user,
                    error = if (user == null) "User not found" else null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    
    fun toggleEditMode() {
        _uiState.value = _uiState.value.copy(isEditing = !_uiState.value.isEditing)
    }
    
    fun updateUser(name: String, email: String, bio: String?) {
        val currentUser = _uiState.value.user ?: return
        
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                val updatedUser = currentUser.copy(
                    name = name,
                    email = email,
                    bio = bio
                )
                userRepository.updateUser(updatedUser)
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    user = updatedUser,
                    isEditing = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }
    
    fun refresh() {
        loadUser()
    }
}