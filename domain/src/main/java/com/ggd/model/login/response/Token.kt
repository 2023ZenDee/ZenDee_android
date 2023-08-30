package com.ggd.model.login.response

data class Token(
    val accessToken: String,
    val refreshToken: String
)