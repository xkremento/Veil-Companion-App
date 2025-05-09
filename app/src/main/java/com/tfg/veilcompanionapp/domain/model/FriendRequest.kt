package com.tfg.veilcompanionapp.domain.model

data class FriendRequest(
    val id: Long,
    val requesterId: String,
    val requesterUsername: String,
    val requesterProfileImageUrl: String?
)