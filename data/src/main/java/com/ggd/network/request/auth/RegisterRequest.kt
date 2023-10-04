package com.ggd.network.request.auth

data class RegisterRequest(
    val email: String,
    val userId: String,
    val password: String,
    val nick: String
)