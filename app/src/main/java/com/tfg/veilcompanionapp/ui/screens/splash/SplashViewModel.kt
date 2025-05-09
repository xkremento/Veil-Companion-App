package com.tfg.veilcompanionapp.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfg.veilcompanionapp.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val authRepository: AuthRepository
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
                val isLoggedIn = authRepository.isLoggedIn()

                if (isLoggedIn) {
                    _navigationState.update { SplashNavigationState.NavigateToHome }
                } else {
                    _navigationState.update { SplashNavigationState.NavigateToAuth }
                }
            } catch (e: Exception) {
                // En caso de error, navegar a la pantalla de autenticación
                _navigationState.update { SplashNavigationState.NavigateToAuth }
            }
        }
    }
}