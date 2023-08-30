package com.ggd.mapper

import com.ggd.model.login.Login
import com.ggd.model.login.LoginDto
import com.ggd.model.login.RefreshToken
import com.ggd.model.login.Token
import com.ggd.model.login.TokenDto
import com.ggd.network.request.login.LoginRequest
import com.ggd.network.response.login.LoginResponse
import com.ggd.network.response.login.RefreshTokenResponse
import com.ggd.network.response.login.TokenResponse
import kr.hs.dgsw.woowasiblings.dgswchat.data.network.request.auth.TokenRequest

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