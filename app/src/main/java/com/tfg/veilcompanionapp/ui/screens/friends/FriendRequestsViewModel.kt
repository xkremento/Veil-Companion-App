package com.tfg.veilcompanionapp.ui.screens.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfg.veilcompanionapp.domain.model.FriendRequest
import com.tfg.veilcompanionapp.utils.DummyDataProvider
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
    // private val friendRepository: FriendRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(FriendRequestsUiState())
    val uiState: StateFlow<FriendRequestsUiState> = _uiState.asStateFlow()

    init {
        loadFriendRequests()
    }

    private fun loadFriendRequests() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // Aquí iría la llamada real al repositorio
                // val requestsList = friendRepository.getFriendRequests()

                // Simulación para desarrollo
                val requestsList = DummyDataProvider.getDummyFriendRequests()

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        friendRequests = requestsList
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar las solicitudes de amistad: ${e.message}"
                    )
                }
            }
        }
    }

    fun acceptFriendRequest(requestId: Long) {
        viewModelScope.launch {
            try {
                // Aquí iría la llamada real al repositorio
                // friendRepository.acceptFriendRequest(requestId)

                // Actualización local para desarrollo
                val updatedRequests = _uiState.value.friendRequests.filter { it.id != requestId }
                _uiState.update { it.copy(friendRequests = updatedRequests) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Error al aceptar la solicitud: ${e.message}")
                }
            }
        }
    }

    fun rejectFriendRequest(requestId: Long) {
        viewModelScope.launch {
            try {
                // Aquí iría la llamada real al repositorio
                // friendRepository.rejectFriendRequest(requestId)

                // Actualización local para desarrollo
                val updatedRequests = _uiState.value.friendRequests.filter { it.id != requestId }
                _uiState.update { it.copy(friendRequests = updatedRequests) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(error = "Error al rechazar la solicitud: ${e.message}")
                }
            }
        }
    }

    fun refreshFriendRequests() {
        loadFriendRequests()
    }
}