package com.ggd.model.auth

data class RegisterResponseModel(
    val status: Int,
    val success: Boolean,
    val message: String,
    val accessToken: String,
    val refreshToken: String
)