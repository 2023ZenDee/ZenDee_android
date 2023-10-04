package com.ggd.model.auth

data class RegisterRequestModel(
    val email: String,
    val userId: String,
    val password: String,
    val nick: String
)