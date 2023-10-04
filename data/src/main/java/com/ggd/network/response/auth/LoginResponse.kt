package com.ggd.network.response.auth

data class LoginResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val accessToken: String,
    val refreshToken: String
)