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

data class RegisterUiState(
    val email: String = "",
    val nickname: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val profileImageUrl: String = "",
    val isLoading: Boolean = false,
    val isRegistrationSuccessful: Boolean = false,
    val errorMessage: String? = null,
    val emailError: String? = null,
    val nicknameError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val profileImageUrlError: String? = null
)

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.update {
            it.copy(
                email = email, emailError = null
            )
        }
    }

    fun updateNickname(nickname: String) {
        _uiState.update {
            it.copy(
                nickname = nickname, nicknameError = null
            )
        }
    }

    fun updatePassword(password: String) {
        _uiState.update {
            it.copy(
                password = password, passwordError = null
            )
        }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.update {
            it.copy(
                confirmPassword = confirmPassword, confirmPasswordError = null
            )
        }
    }

    fun updateProfileImageUrl(profileImageUrl: String) {
        _uiState.update {
            it.copy(
                profileImageUrl = profileImageUrl, profileImageUrlError = null
            )
        }
    }

    fun register() {
        if (!validateInputs()) {
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val profileImageUrl = _uiState.value.profileImageUrl.takeIf { it.isNotBlank() }

            when (val result = authRepository.register(
                email = _uiState.value.email,
                nickname = _uiState.value.nickname,
                password = _uiState.value.password,
                profileImageUrl = profileImageUrl
            )) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false, isRegistrationSuccessful = true
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.exception.message
                                ?: "Error desconocido al registrar"
                        )
                    }
                }

                else -> { /* Ignore loading state */
                }
            }
        }
    }

    private fun validateInputs(): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val nicknamePattern = "^[a-zA-Z0-9_]+$"
        val passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$"
        val urlPattern = "^(https?|ftp)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]"

        val emailError = when {
            _uiState.value.email.isBlank() -> "El email no puede estar vacío"
            !_uiState.value.email.matches(emailPattern.toRegex()) -> "Introduce un email válido"
            else -> null
        }

        val nicknameError = when {
            _uiState.value.nickname.isBlank() -> "El nickname no puede estar vacío"
            _uiState.value.nickname.length < 3 -> "El nickname debe tener al menos 3 caracteres"
            _uiState.value.nickname.length > 30 -> "El nickname no puede tener más de 30 caracteres"
            !_uiState.value.nickname.matches(nicknamePattern.toRegex()) -> "El nickname solo puede contener letras, números y guiones bajos"
            else -> null
        }

        val passwordError = when {
            _uiState.value.password.isBlank() -> "La contraseña no puede estar vacía"
            _uiState.value.password.length < 8 -> "La contraseña debe tener al menos 8 caracteres"
            _uiState.value.password.length > 128 -> "La contraseña no puede tener más de 128 caracteres"
            !_uiState.value.password.matches(passwordPattern.toRegex()) -> "La contraseña debe contener al menos un número, una letra minúscula, una letra mayúscula y un carácter especial"
            else -> null
        }

        val confirmPasswordError = when {
            _uiState.value.confirmPassword.isBlank() -> "Debes confirmar la contraseña"
            _uiState.value.confirmPassword != _uiState.value.password -> "Las contraseñas no coinciden"
            else -> null
        }

        val profileImageUrlError = when {
            _uiState.value.profileImageUrl.isNotBlank() && !_uiState.value.profileImageUrl.matches(
                urlPattern.toRegex()
            ) -> "Introduce una URL válida para la imagen de perfil"

            else -> null
        }

        _uiState.update {
            it.copy(
                emailError = emailError,
                nicknameError = nicknameError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError,
                profileImageUrlError = profileImageUrlError
            )
        }

        return emailError == null && nicknameError == null && passwordError == null && confirmPasswordError == null && profileImageUrlError == null
    }

    fun resetState() {
        _uiState.update { RegisterUiState() }
    }
}