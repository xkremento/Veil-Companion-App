package com.tfg.veilcompanionapp.ui.screens.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfg.veilcompanionapp.domain.model.Friend
import com.tfg.veilcompanionapp.utils.DummyDataProvider
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
    // private val friendRepository: FriendRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FriendsUiState())
    val uiState: StateFlow<FriendsUiState> = _uiState.asStateFlow()

    init {
        loadFriends()
    }

    private fun loadFriends() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // Aquí iría la llamada real al repositorio
                // val friendsList = friendRepository.getFriends()

                // Simulación para desarrollo
                val friendsList = DummyDataProvider.getDummyFriends()

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        friends = friendsList
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar los amigos: ${e.message}"
                    )
                }
            }
        }
    }

    fun deleteFriend(friendId: String) {
        viewModelScope.launch {
            try {
                // Aquí iría la llamada real al repositorio
                // friendRepository.removeFriend(friendId)

                // Actualización local para desarrollo
                val updatedFriends = _uiState.value.friends.filter { it.id != friendId }
                _uiState.update { it.copy(friends = updatedFriends) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Error al eliminar el amigo: ${e.message}")
                }
            }
        }
    }

    fun refreshFriends() {
        loadFriends()
    }
}