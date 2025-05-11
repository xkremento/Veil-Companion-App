package com.tfg.veilcompanionapp.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfg.veilcompanionapp.data.repository.AuthRepository
import com.tfg.veilcompanionapp.domain.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val isLoginSuccessful: Boolean = false,
    val errorMessage: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.update { it.copy(
            email = email,
            emailError = null
        ) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(
            password = password,
            passwordError = null
        ) }
    }

    fun login() {
        if (!validateInputs()) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            when (val result = authRepository.login(_uiState.value.email, _uiState.value.password)) {
                is Result.Success -> {
                    _uiState.update { it.copy(
                        isLoading = false,
                        isLoginSuccessful = true
                    ) }
                }
                is Result.Error -> {
                    _uiState.update { it.copy(
                        isLoading = false,
                        errorMessage = result.exception.message ?: "Error desconocido al iniciar sesión"
                    ) }
                }
                else -> { /* Ignorar estado Loading */ }
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

        val passwordError = when {
            _uiState.value.password.isBlank() -> "La contraseña no puede estar vacía"
            _uiState.value.password.length < 8 -> "La contraseña debe tener al menos 8 caracteres"
            else -> null
        }

        _uiState.update { it.copy(
            emailError = emailError,
            passwordError = passwordError
        ) }

        return emailError == null && passwordError == null
    }

    fun resetState() {
        _uiState.update { LoginUiState() }
    }
}