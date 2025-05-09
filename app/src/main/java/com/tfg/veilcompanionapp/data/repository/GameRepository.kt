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
                    // Buscamos si somos asesinos en esta partida
                    val playerData = dto.players.find { it.isMurderer }
                    val isMurderer = playerData != null

                    // Formateamos la fecha/hora
                    val gameDateTime = playerData?.gameDateTime ?: ""

                    // Determinamos el ganador (esto dependerá de la lógica de tu juego)
                    val winner = "" // Información no disponible directamente en la API

                    // Formato de duración (minutos:segundos)
                    val minutes = dto.duration / 60
                    val seconds = dto.duration % 60
                    val duration = String.format("%02d:%02d", minutes, seconds)

                    Game(
                        id = dto.id,
                        date = formatDateTime(gameDateTime),
                        role = if (isMurderer) "Asesino" else "Inocente",
                        duration = duration,
                        winner = winner,
                        reward = "+10 pesos" // Información no disponible directamente en la API
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
            // Suponiendo que la API devuelve una fecha en formato ISO 8601
            val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

            val date = inputFormat.parse(dateTimeString)
            outputFormat.format(date!!)
        } catch (e: Exception) {
            dateTimeString // Devolver el original si hay error
        }
    }
}