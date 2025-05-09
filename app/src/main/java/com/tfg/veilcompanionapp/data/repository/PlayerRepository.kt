package com.tfg.veilcompanionapp.data.repository

import com.tfg.veilcompanionapp.data.api.ApiService
import com.tfg.veilcompanionapp.data.api.models.PasswordUpdateDTO
import com.tfg.veilcompanionapp.data.api.models.ProfileImageDTO
import com.tfg.veilcompanionapp.domain.model.Player
import com.tfg.veilcompanionapp.domain.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlayerRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getCurrentPlayer(): Result<Player> {
        return try {
            val response = apiService.getCurrentPlayer()

            if (response.isSuccessful && response.body() != null) {
                val playerDto = response.body()!!

                // Mapear DTO a modelo de dominio
                val player = Player(
                    email = playerDto.email,
                    nickname = playerDto.nickname,
                    coins = playerDto.coins,
                    skinUrl = playerDto.skinUrl,
                    profileImageUrl = playerDto.profileImageUrl
                )

                Result.Success(player)
            } else {
                Result.Error(Exception("Error al obtener el perfil: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun changePassword(newPassword: String): Result<Player> {
        return try {
            val response = apiService.changePassword(PasswordUpdateDTO(newPassword))

            if (response.isSuccessful && response.body() != null) {
                val playerDto = response.body()!!

                // Mapear DTO a modelo de dominio
                val player = Player(
                    email = playerDto.email,
                    nickname = playerDto.nickname,
                    coins = playerDto.coins,
                    skinUrl = playerDto.skinUrl,
                    profileImageUrl = playerDto.profileImageUrl
                )

                Result.Success(player)
            } else {
                Result.Error(Exception("Error al cambiar la contraseña: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun updateProfileImage(imageUrl: String): Result<Player> {
        return try {
            val response = apiService.updateProfileImage(ProfileImageDTO(imageUrl))

            if (response.isSuccessful && response.body() != null) {
                val playerDto = response.body()!!

                // Mapear DTO a modelo de dominio
                val player = Player(
                    email = playerDto.email,
                    nickname = playerDto.nickname,
                    coins = playerDto.coins,
                    skinUrl = playerDto.skinUrl,
                    profileImageUrl = playerDto.profileImageUrl
                )

                Result.Success(player)
            } else {
                Result.Error(Exception("Error al actualizar la imagen de perfil: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun deletePlayer(): Result<Unit> {
        return try {
            val response = apiService.deleteCurrentPlayer()

            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Error al eliminar la cuenta: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}