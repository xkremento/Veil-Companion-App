package com.tfg.veilcompanionapp.ui.screens.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfg.veilcompanionapp.data.repository.FriendRepository
import com.tfg.veilcompanionapp.domain.model.Friend
import com.tfg.veilcompanionapp.domain.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FriendsUiState(
    val isLoading: Boolean = false,
    val friends: List<Friend> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendRepository: FriendRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FriendsUiState())
    val uiState: StateFlow<FriendsUiState> = _uiState.asStateFlow()

    init {
        loadFriends()
    }

    private fun loadFriends() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            when (val result = friendRepository.getFriends()) {
                is Result.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false, friends = result.data
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Error al cargar los amigos: ${result.exception.message}"
                        )
                    }
                }

                else -> { /* Ignore loading state */
                }
            }
        }
    }

    fun deleteFriend(friendId: String) {
        viewModelScope.launch {
            when (val result = friendRepository.removeFriend(friendId)) {
                is Result.Success -> {
                    // Update the friends list locally
                    val updatedFriends = _uiState.value.friends.filter { it.id != friendId }
                    _uiState.update { it.copy(friends = updatedFriends) }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(error = "Error al eliminar el amigo: ${result.exception.message}")
                    }
                }

                else -> { /* Ignore loading state */
                }
            }
        }
    }

    fun refreshFriends() {
        loadFriends()
    }
}