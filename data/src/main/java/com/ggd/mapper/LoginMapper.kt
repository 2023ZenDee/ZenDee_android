package com.ggd.mapper


import com.ggd.model.login.requests.LoginDto
import com.ggd.model.login.requests.TokenDto
import com.ggd.model.login.response.Login
import com.ggd.model.login.response.RefreshToken
import com.ggd.model.login.response.Token
import com.ggd.network.request.login.LoginRequest
import com.ggd.network.response.login.LoginResponse
import com.ggd.network.response.login.RefreshTokenResponse
import com.ggd.network.response.login.TokenResponse
import com.ggd.network.request.login.TokenRequest

fun LoginDto.toModel(): LoginRequest = LoginRequest(
    userId = this.userId,
    password = this.password
)

fun TokenDto.toModel(): TokenRequest = TokenRequest(
    authCode = this.authCode
)

fun TokenResponse.toEntity(): Token = Token(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)

fun RefreshTokenResponse.toEntity(): RefreshToken = RefreshToken(
    accessToken = this.accessToken
)

fun LoginResponse.toEntity(): Login = Login(
    authCode = this.authCode
)