package com.tfg.veilcompanionapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfg.veilcompanionapp.data.repository.GameRepository
import com.tfg.veilcompanionapp.data.repository.PlayerRepository
import com.tfg.veilcompanionapp.data.repository.AuthRepository
import com.tfg.veilcompanionapp.data.repository.FriendRepository
import com.tfg.veilcompanionapp.domain.model.Game
import com.tfg.veilcompanionapp.domain.model.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val isLoading: Boolean = false,
    val username: String = "",
    val profileImageUrl: String? = null,
    val friends: Int = 0,
    val coins: Int = 0,
    val games: List<Game> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val playerRepository: PlayerRepository,
    private val gameRepository: GameRepository,
    private val friendRepository: FriendRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadUserData()
        loadFriends()
        loadGames()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            when (val result = playerRepository.getCurrentPlayer()) {
                is Result.Success -> {
                    val player = result.data
                    _uiState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            username = player.nickname,
                            profileImageUrl = player.profileImageUrl,
                            coins = player.coins
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = "Error al cargar los datos del usuario: ${result.exception.message}"
                        )
                    }
                }

                else -> { /* Ignore loading state */ }
            }
        }
    }

    private fun loadFriends() {
        viewModelScope.launch {
            when (val result = friendRepository.getFriends()) {
                is Result.Success -> {
                    _uiState.update { currentState ->
                        currentState.copy(
                            friends = result.data.size
                        )
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            error = "Error al cargar los amigos: ${result.exception.message}"
                        )
                    }
                }

                else -> { /* Ignore loading state */ }
            }
        }
    }

    private fun loadGames() {
        viewModelScope.launch {
            when (val result = gameRepository.getUserGames()) {
                is Result.Success -> {
                    // Actualizar el texto de recompensa para cambiar "pesos" a "monedas"
                    val updatedGames = result.data.map { game ->
                        game.copy(reward = game.reward.replace("pesos", "monedas"))
                    }

                    _uiState.update { currentState ->
                        currentState.copy(games = updatedGames)
                    }
                }

                is Result.Error -> {
                    _uiState.update {
                        it.copy(
                            error = "Error al cargar las partidas: ${result.exception.message}"
                        )
                    }
                }

                else -> { /* Ignore loading state */ }
            }
        }
    }

    fun refreshData() {
        loadUserData()
        loadFriends()
        loadGames()
    }

    fun logout() {
        viewModelScope.launch {
            try {
                authRepository.logout()
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = "Error al cerrar sesión: ${e.message}"
                    )
                }
            }
        }
    }
}