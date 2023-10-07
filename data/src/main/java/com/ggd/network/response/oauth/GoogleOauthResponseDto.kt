package com.ggd.network.response.oauth

data class GoogleOauthResponseDto(
    val status: Int,
    val success: Boolean,
    val message : String,
    val accessToken : String,
    val refreshToken : String
)