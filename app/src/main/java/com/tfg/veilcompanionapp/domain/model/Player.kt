package com.tfg.veilcompanionapp.domain.model

data class Player(
    val email: String,
    val nickname: String,
    val coins: Int,
    val skinUrl: String? = null,
    val profileImageUrl: String? = null
)