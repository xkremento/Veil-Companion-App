package com.tfg.veilcompanionapp.data.api.models

data class AuthRequest(
    val email: String,
    val password: String
)

data class AuthResponse(
    val token: String,
    val email: String,
    val nickname: String
)