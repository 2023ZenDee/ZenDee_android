package com.ggd.network.response.login

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
