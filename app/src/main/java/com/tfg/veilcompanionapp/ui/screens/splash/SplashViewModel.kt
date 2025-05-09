package com.tfg.veilcompanionapp.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfg.veilcompanionapp.data.local.datastore.UserPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SplashNavigationState {
    object Loading : SplashNavigationState()
    object NavigateToAuth : SplashNavigationState()
    object NavigateToHome : SplashNavigationState()
}

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userPreferences: UserPreferences
    // private val authRepository: AuthRepository (si necesitas validar el token con tu API)
) : ViewModel() {

    private val _navigationState = MutableStateFlow<SplashNavigationState>(SplashNavigationState.Loading)
    val navigationState: StateFlow<SplashNavigationState> = _navigationState.asStateFlow()

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            // Añadir un pequeño retraso para mostrar la animación
            delay(1500)

            try {
                // Obtener el token JWT del DataStore
                val token = userPreferences.authToken.first()

                if (token != null) {
                    // Opcional: Verificar si el token es válido con una llamada a la API
                    // val isTokenValid = authRepository.validateToken(token)

                    // Por ahora, asumiremos que cualquier token existente es válido
                    val isTokenValid = true

                    if (isTokenValid) {
                        _navigationState.update { SplashNavigationState.NavigateToHome }
                    } else {
                        // Si el token no es válido, navegar a la pantalla de autenticación
                        _navigationState.update { SplashNavigationState.NavigateToAuth }
                    }
                } else {
                    // No hay token, navegar a la pantalla de autenticación
                    _navigationState.update { SplashNavigationState.NavigateToAuth }
                }
            } catch (e: Exception) {
                // En caso de error, navegar a la pantalla de autenticación
                _navigationState.update { SplashNavigationState.NavigateToAuth }
            }
        }
    }
}