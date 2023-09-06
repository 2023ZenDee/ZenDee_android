package com.ggd.network.response.login

data class LoginResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val accessToken: String,
    val refreshToken: String
)