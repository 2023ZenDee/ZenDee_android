package com.ggd.mapper


import com.ggd.model.auth.LoginRequestModel
import com.ggd.model.auth.LoginResponseModel
import com.ggd.model.auth.RegisterRequestModel
import com.ggd.model.auth.RegisterResponseModel
import com.ggd.network.request.auth.LoginRequest
import com.ggd.network.request.auth.RegisterRequest
import com.ggd.network.response.auth.LoginResponse
import com.ggd.network.response.auth.RegisterResponse

fun LoginRequestModel.toDto(): LoginRequest = LoginRequest(
    userId = this.userId,
    password = this.password
)

fun LoginResponse.toModel(): LoginResponseModel = LoginResponseModel(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)

fun RegisterRequestModel.toDto() = RegisterRequest(
    email = this.email,
    userId = this.userId,
    password = this.password,
    nick = this.nick
)

fun RegisterResponse.toModel() = RegisterResponseModel(
    status = this.status,
    success = this.success,
    message = this.message,
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)
