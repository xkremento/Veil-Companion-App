package com.tfg.veilcompanionapp.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tfg.veilcompanionapp.domain.model.Game
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
    val points: Int = 0,
    val friends: Int = 0,
    val coins: Int = 0,
    val games: List<Game> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    // private val playerRepository: PlayerRepository,
    // private val gameRepository: GameRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadUserData()
        loadGames()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // Aquí iría la llamada real al repositorio
                // val user = playerRepository.getCurrentUserProfile()

                // Simulación para desarrollo
                val user = DummyData.getDummyUser()

                _uiState.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        username = user.username,
                        profileImageUrl = user.profileImageUrl,
                        points = user.points,
                        friends = user.friends,
                        coins = user.coins
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Error al cargar los datos del usuario: ${e.message}"
                    )
                }
            }
        }
    }

    private fun loadGames() {
        viewModelScope.launch {
            try {
                // Aquí iría la llamada real al repositorio
                // val gamesList = gameRepository.getUserGames()

                // Simulación para desarrollo
                val gamesList = DummyData.getDummyGames()

                _uiState.update { currentState ->
                    currentState.copy(games = gamesList)
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = "Error al cargar las partidas: ${e.message}"
                    )
                }
            }
        }
    }

    fun refreshData() {
        loadUserData()
        loadGames()
    }
}

// Clase temporal para datos de prueba
private object DummyData {
    data class DummyUser(
        val username: String,
        val profileImageUrl: String?,
        val points: Int,
        val friends: Int,
        val coins: Int
    )

    fun getDummyUser() = DummyUser(
        username = "Username",
        profileImageUrl = null,
        points = 100,
        friends = 5,
        coins = 200
    )

    fun getDummyGames() = listOf(
        Game(
            id = 1L,
            date = "01/01/1970",
            role = "Asesino / Inocente",
            duration = "01:01",
            winner = "",
            reward = "+10 pesos"
        ),
        Game(
            id = 2L,
            date = "01/01/1970",
            role = "Asesino / Inocente",
            duration = "01:01",
            winner = "",
            reward = "+10 pesos"
        )
    )
}