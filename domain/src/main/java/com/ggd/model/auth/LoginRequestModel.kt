package com.ggd.model.auth

data class LoginRequestModel(
    val userId: String,
    val password: String
)