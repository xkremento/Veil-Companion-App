package com.tfg.veilcompanionapp.ui.screens.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfg.veilcompanionapp.data.repository.FriendRepository
import com.tfg.veilcompanionapp.data.repository.PlayerRepository
import com.tfg.veilcompanionapp.domain.model.FriendRequest
import com.tfg.veilcompanionapp.domain.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FriendRequestsUiState(
    val isLoading: Boolean = false,
    val friendRequests: List<FriendRequest> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class FriendRequestsViewModel @Inject constructor(
    private val friendRepository: FriendRepository,
    private val playerRepository: PlayerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FriendRequestsUiState())
    val uiState: StateFlow<FriendRequestsUiState> = _uiState.asStateFlow()

    init {
        loadFriendRequests()
    }

    private fun loadFriendRequests() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            when (val result = friendRepository.getFriendRequests()) {
                is Result.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            friendRequests = result.data
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Error loading friend requests: ${result.exception.message}"
                        )
                    }
                }

                else -> { /* Ignore loading state */ }
            }
        }
    }

    fun acceptFriendRequest(requestId: Long) {
        viewModelScope.launch {
            when (val result = friendRepository.acceptFriendRequest(requestId)) {
                is Result.Success -> {
                    // Update the requests list locally
                    val updatedRequests =
                        _uiState.value.friendRequests.filter { it.id != requestId }
                    _uiState.update { it.copy(friendRequests = updatedRequests) }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(error = "Error accepting request: ${result.exception.message}")
                    }
                }

                else -> { /* Ignore loading state */ }
            }
        }
    }

    fun rejectFriendRequest(requestId: Long) {
        viewModelScope.launch {
            when (val result = friendRepository.rejectFriendRequest(requestId)) {
                is Result.Success -> {
                    // Update the requests list locally
                    val updatedRequests =
                        _uiState.value.friendRequests.filter { it.id != requestId }
                    _uiState.update { it.copy(friendRequests = updatedRequests) }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(error = "Error rejecting request: ${result.exception.message}")
                    }
                }

                else -> { /* Ignore loading state */ }
            }
        }
    }

    fun refreshFriendRequests() {
        loadFriendRequests()
    }
}