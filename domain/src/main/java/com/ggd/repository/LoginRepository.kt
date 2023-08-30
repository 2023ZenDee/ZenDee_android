package com.ggd.repository

import com.ggd.model.login.Login
import com.ggd.model.login.LoginDto
import com.ggd.model.login.RefreshToken
import com.ggd.model.login.Token
import com.ggd.model.login.TokenDto
import dagger.Component
import dagger.Provides

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