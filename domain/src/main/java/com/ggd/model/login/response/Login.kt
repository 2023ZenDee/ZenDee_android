package com.ggd.model.login.response

data class Login(
    val accessToken: String,
    val refreshToken: String
)