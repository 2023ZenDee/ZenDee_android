package com.ggd.network.request.login

data class RegisterRequest(
    val userId: String,
    val nickname: String,
    val password: String,
    val grade: Int,
    val room: Int,
    val number: Int
)
