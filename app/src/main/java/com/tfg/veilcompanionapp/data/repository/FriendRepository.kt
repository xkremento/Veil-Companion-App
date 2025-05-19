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
            val requestDto = CreateFriendRequestDTO(
                requesterId = "", playerId = friendEmail
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
                        requesterUsername = dto.requesterNickname,
                        requesterProfileImageUrl = dto.requesterProfileImageUrl
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
                    profileImageUrl = friendDto.profileImageUrl,
                    friendshipDate = friendDto.friendshipDate
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
                        profileImageUrl = dto.profileImageUrl,
                        friendshipDate = dto.friendshipDate
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