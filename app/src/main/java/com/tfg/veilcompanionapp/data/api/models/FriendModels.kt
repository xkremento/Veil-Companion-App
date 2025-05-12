package com.tfg.veilcompanionapp.data.api.models

data class CreateFriendRequestDTO(
    val requesterId: String, val playerId: String
)

data class FriendRequestDTO(
    val friendRequestId: Long, val requesterId: String, val playerId: String
)

data class FriendResponseDTO(
    val email: String, val nickname: String, val friendshipDate: String
)