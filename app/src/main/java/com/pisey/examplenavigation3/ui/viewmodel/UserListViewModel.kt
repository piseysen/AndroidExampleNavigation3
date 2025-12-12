package com.pisey.examplenavigation3.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pisey.examplenavigation3.data.model.User
import com.pisey.examplenavigation3.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UserListUiState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val error: String? = null,
    val sortOrder: SortOrder = SortOrder.NAME_ASC
)

enum class SortOrder {
    NAME_ASC, NAME_DESC, EMAIL_ASC, EMAIL_DESC
}

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow(UserListUiState())
    val uiState: StateFlow<UserListUiState> = _uiState.asStateFlow()
    
    init {
        observeUsers()
    }
    
    private fun observeUsers() {
        userRepository.getUsersFlow()
            .onEach { users ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    users = sortUsers(users, _uiState.value.sortOrder),
                    error = null
                )
            }
            .catch { e ->
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
            .launchIn(viewModelScope)
    }
    
    fun changeSortOrder(sortOrder: SortOrder) {
        _uiState.value = _uiState.value.copy(
            sortOrder = sortOrder,
            users = sortUsers(_uiState.value.users, sortOrder)
        )
    }
    
    private fun sortUsers(users: List<User>, sortOrder: SortOrder): List<User> {
        return when (sortOrder) {
            SortOrder.NAME_ASC -> users.sortedBy { it.name }
            SortOrder.NAME_DESC -> users.sortedByDescending { it.name }
            SortOrder.EMAIL_ASC -> users.sortedBy { it.email }
            SortOrder.EMAIL_DESC -> users.sortedByDescending { it.email }
        }
    }
    
    fun refresh() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            observeUsers()
        }
    }
}