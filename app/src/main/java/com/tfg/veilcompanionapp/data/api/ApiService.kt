package com.tfg.veilcompanionapp.data.api

import com.tfg.veilcompanionapp.data.api.models.AddCoinsRequest
import com.tfg.veilcompanionapp.data.api.models.AuthRequest
import com.tfg.veilcompanionapp.data.api.models.AuthResponse
import com.tfg.veilcompanionapp.data.api.models.CreateFriendRequestDTO
import com.tfg.veilcompanionapp.data.api.models.FriendRequestDTO
import com.tfg.veilcompanionapp.data.api.models.FriendResponseDTO
import com.tfg.veilcompanionapp.data.api.models.GameCreationDTO
import com.tfg.veilcompanionapp.data.api.models.GameResponseDTO
import com.tfg.veilcompanionapp.data.api.models.PasswordUpdateDTO
import com.tfg.veilcompanionapp.data.api.models.PlayerRegistrationDTO
import com.tfg.veilcompanionapp.data.api.models.PlayerResponseDTO
import com.tfg.veilcompanionapp.data.api.models.ProfileImageDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    // Auth endpoints
    @POST("/api/auth/login")
    suspend fun login(@Body authRequest: AuthRequest): Response<AuthResponse>

    @POST("/api/auth/register")
    suspend fun register(@Body playerDto: PlayerRegistrationDTO): Response<PlayerResponseDTO>

    // Player endpoints
    @GET("/api/players/me")
    suspend fun getCurrentPlayer(): Response<PlayerResponseDTO>

    @PUT("/api/players/password")
    suspend fun changePassword(@Body passwordUpdateDTO: PasswordUpdateDTO): Response<PlayerResponseDTO>

    @PUT("/api/players/profile-image")
    suspend fun updateProfileImage(@Body profileImageDTO: ProfileImageDTO): Response<PlayerResponseDTO>

    @DELETE("/api/players")
    suspend fun deleteCurrentPlayer(): Response<Unit>

    // Friend endpoints
    @POST("/api/friends/requests")
    suspend fun sendFriendRequest(@Body requestDto: CreateFriendRequestDTO): Response<Map<String, Long>>

    @POST("/api/friends/requests/{requestId}/accept")
    suspend fun acceptFriendRequest(@Path("requestId") requestId: Long): Response<FriendResponseDTO>

    @POST("/api/friends/requests/{requestId}/decline")
    suspend fun declineFriendRequest(@Path("requestId") requestId: Long): Response<Unit>

    @GET("/api/friends/requests")
    suspend fun getFriendRequests(): Response<List<FriendRequestDTO>>

    @GET("/api/friends")
    suspend fun getFriends(): Response<List<FriendResponseDTO>>

    @DELETE("/api/friends/{friendEmail}")
    suspend fun removeFriend(@Path("friendEmail") friendEmail: String): Response<Unit>

    // Game endpoints
    @POST("/api/games")
    suspend fun createGame(@Body gameDto: GameCreationDTO): Response<GameResponseDTO>

    @GET("/api/games/{gameId}")
    suspend fun getGame(@Path("gameId") gameId: Long): Response<GameResponseDTO>

    @GET("/api/games")
    suspend fun getMyGames(): Response<List<GameResponseDTO>>
}