package com.tfg.veilcompanionapp.ui.screens.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class AddFriendUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val isRequestSent: Boolean = false,
    val errorMessage: String? = null,
    val emailError: String? = null
)

@HiltViewModel
class AddFriendViewModel @Inject constructor(
    // private val friendRepository: FriendRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddFriendUiState())
    val uiState: StateFlow<AddFriendUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.update { it.copy(
            email = email,
            emailError = null
        ) }
    }

    fun sendFriendRequest() {
        if (!validateInputs()) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                // Aquí iría la llamada real al repositorio
                // val requestId = friendRepository.sendFriendRequest(_uiState.value.email)

                // Simulación para desarrollo - esperamos 1 segundo para simular la llamada a la API
                kotlinx.coroutines.delay(1000)

                // Simulamos que el envío es exitoso solo con fines de desarrollo
                _uiState.update { it.copy(
                    isLoading = false,
                    isRequestSent = true
                ) }
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    errorMessage = "Error al enviar la solicitud: ${e.message}"
                ) }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val emailError = when {
            _uiState.value.email.isBlank() -> "El email no puede estar vacío"
            !_uiState.value.email.matches(emailPattern.toRegex()) -> "Introduce un email válido"
            else -> null
        }

        _uiState.update { it.copy(
            emailError = emailError
        ) }

        return emailError == null
    }

    fun resetState() {
        _uiState.update { AddFriendUiState() }
    }
}