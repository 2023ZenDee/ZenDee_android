package com.ggd.repository

import com.ggd.model.login.requests.LoginDto
import com.ggd.model.login.requests.TokenDto
import com.ggd.model.login.response.Login
import com.ggd.model.login.response.RefreshToken
import com.ggd.model.login.response.Token

interface LoginRepository {

    suspend fun login(
        loginDto: LoginDto
    ): Login

    suspend fun refreshToken(
    ): RefreshToken

    suspend fun token(
        tokenDto: TokenDto
    ): Token

}