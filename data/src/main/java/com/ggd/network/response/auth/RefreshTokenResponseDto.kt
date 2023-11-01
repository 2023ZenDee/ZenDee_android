package com.ggd.network.response.auth

data class RefreshTokenResponseDto (
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: String,
)