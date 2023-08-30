package com.ggd.model.login

data class Token(
    val accessToken: String,
    val refreshToken: String
)