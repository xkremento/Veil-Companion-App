package com.tfg.veilcompanionapp.data.repository

import com.tfg.veilcompanionapp.data.api.ApiService
import com.tfg.veilcompanionapp.data.api.models.AuthRequest
import com.tfg.veilcompanionapp.data.api.models.AuthResponse
import com.tfg.veilcompanionapp.data.api.models.PlayerRegistrationDTO
import com.tfg.veilcompanionapp.data.local.datastore.UserPreferences
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton
import com.tfg.veilcompanionapp.domain.model.Result

@Singleton
class AuthRepository @Inject constructor(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) {

    suspend fun login(email: String, password: String): Result<AuthResponse> {
        return try {
            val response = apiService.login(AuthRequest(email, password))

            if (response.isSuccessful && response.body() != null) {
                val authResponse = response.body()!!

                // Guardar el token JWT y la información del usuario
                userPreferences.saveAuthToken(authResponse.token)
                userPreferences.saveUserInfo(authResponse.email, authResponse.nickname)

                Result.Success(authResponse)
            } else {
                Result.Error(Exception("Error al iniciar sesión: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun register(
        email: String,
        nickname: String,
        password: String,
        profileImageUrl: String? = null
    ): Result<Unit> {
        return try {
            val playerDto = PlayerRegistrationDTO(
                email = email,
                nickname = nickname,
                password = password,
                profileImageUrl = profileImageUrl
            )

            val response = apiService.register(playerDto)

            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Error al registrar: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun logout() {
        userPreferences.clearData()
    }

    suspend fun isLoggedIn(): Boolean {
        val token = userPreferences.authToken.first()
        return !token.isNullOrEmpty()
    }
}