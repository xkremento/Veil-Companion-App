package com.tfg.veilcompanionapp.data.api.models

data class PlayerRegistrationDTO(
    val email: String,
    val nickname: String,
    val password: String,
    val skinUrl: String? = null,
    val profileImageUrl: String? = null
)

data class PlayerResponseDTO(
    val email: String,
    val nickname: String,
    val coins: Int,
    val skinUrl: String?,
    val profileImageUrl: String?
)

data class PasswordUpdateDTO(
    val password: String
)

data class ProfileImageDTO(
    val profileImageUrl: String
)

data class AddCoinsRequest(
    val amount: Int
)