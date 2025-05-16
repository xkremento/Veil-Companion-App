package com.tfg.veilcompanionapp.data.repository

import com.tfg.veilcompanionapp.data.api.ApiService
import com.tfg.veilcompanionapp.domain.model.Game
import com.tfg.veilcompanionapp.domain.model.Result
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GameRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getUserGames(): Result<List<Game>> {
        return try {
            val response = apiService.getMyGames()

            if (response.isSuccessful && response.body() != null) {
                val games = response.body()!!.map { dto ->
                    // Format date/time
                    val gameDateTime = dto.players.firstOrNull()?.gameDateTime ?: ""

                    // Duration format (minutes:seconds)
                    val minutes = dto.duration / 60
                    val seconds = dto.duration % 60
                    val duration = String.format("%02d:%02d", minutes, seconds)

                    Game(
                        id = dto.id,
                        date = formatDateTime(gameDateTime),
                        role = "",
                        duration = duration
                    )
                }

                Result.Success(games)
            } else {
                Result.Error(Exception("Error al obtener las partidas: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun formatDateTime(dateTimeString: String): String {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            val date = inputFormat.parse(dateTimeString)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            dateTimeString // Fallback to original string if parsing fails
        }
    }

    suspend fun wasPlayerMurdererInGame(gameId: Long): Result<Boolean> {
        return try {
            val response = apiService.checkIfPlayerWasMurderer(gameId)

            if (response.isSuccessful && response.body() != null) {
                val wasMurderer = response.body()!!["wasMurderer"] ?: false
                Result.Success(wasMurderer)
            } else {
                Result.Error(Exception("Error al comprobar el rol: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}