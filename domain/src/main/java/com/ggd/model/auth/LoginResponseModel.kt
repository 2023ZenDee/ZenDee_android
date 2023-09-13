package com.ggd.model.auth

data class LoginResponseModel(
    val accessToken: String,
    val refreshToken: String
)