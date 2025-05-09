package com.tfg.veilcompanionapp.data.repository

import com.tfg.veilcompanionapp.data.api.ApiService
import com.tfg.veilcompanionapp.data.api.models.CreateFriendRequestDTO
import com.tfg.veilcompanionapp.domain.model.Friend
import com.tfg.veilcompanionapp.domain.model.FriendRequest
import com.tfg.veilcompanionapp.domain.model.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FriendRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun sendFriendRequest(friendEmail: String): Result<Long> {
        return try {
            // La API espera el email del solicitante, pero el interceptor ya incluye el token
            // Por lo que podemos usar una cadena vacía o cualquier placeholder, ya que el backend lo ignorará
            val requestDto = CreateFriendRequestDTO(
                requesterId = "",
                playerId = friendEmail
            )

            val response = apiService.sendFriendRequest(requestDto)

            if (response.isSuccessful && response.body() != null) {
                val requestId = response.body()!!["requestId"] ?: 0L
                Result.Success(requestId)
            } else {
                Result.Error(Exception("Error al enviar la solicitud: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getFriendRequests(): Result<List<FriendRequest>> {
        return try {
            val response = apiService.getFriendRequests()

            if (response.isSuccessful && response.body() != null) {
                val requests = response.body()!!.map { dto ->
                    FriendRequest(
                        id = dto.friendRequestId,
                        requesterId = dto.requesterId,
                        requesterUsername = "", // API no proporciona el nombre de usuario en la solicitud
                        requesterProfileImageUrl = null // API no proporciona la imagen en la solicitud
                    )
                }

                Result.Success(requests)
            } else {
                Result.Error(Exception("Error al obtener las solicitudes: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun acceptFriendRequest(requestId: Long): Result<Friend> {
        return try {
            val response = apiService.acceptFriendRequest(requestId)

            if (response.isSuccessful && response.body() != null) {
                val friendDto = response.body()!!

                val friend = Friend(
                    id = friendDto.email,
                    username = friendDto.nickname,
                    profileImageUrl = null // API no proporciona la imagen en la respuesta
                )

                Result.Success(friend)
            } else {
                Result.Error(Exception("Error al aceptar la solicitud: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun rejectFriendRequest(requestId: Long): Result<Unit> {
        return try {
            val response = apiService.declineFriendRequest(requestId)

            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Error al rechazar la solicitud: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun getFriends(): Result<List<Friend>> {
        return try {
            val response = apiService.getFriends()

            if (response.isSuccessful && response.body() != null) {
                val friends = response.body()!!.map { dto ->
                    Friend(
                        id = dto.email,
                        username = dto.nickname,
                        profileImageUrl = null // API no proporciona la imagen en la respuesta
                    )
                }

                Result.Success(friends)
            } else {
                Result.Error(Exception("Error al obtener los amigos: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun removeFriend(friendEmail: String): Result<Unit> {
        return try {
            val response = apiService.removeFriend(friendEmail)

            if (response.isSuccessful) {
                Result.Success(Unit)
            } else {
                Result.Error(Exception("Error al eliminar el amigo: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}