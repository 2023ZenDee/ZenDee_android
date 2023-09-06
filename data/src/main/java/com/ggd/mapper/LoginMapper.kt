package com.ggd.mapper


import com.ggd.model.login.requests.LoginDto
import com.ggd.model.login.response.Login
import com.ggd.network.request.login.LoginRequest
import com.ggd.network.response.login.LoginResponse

fun LoginDto.toModel(): LoginRequest = LoginRequest(
    userId = this.userId,
    password = this.password
)

fun LoginResponse.toEntity(): Login = Login(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)


