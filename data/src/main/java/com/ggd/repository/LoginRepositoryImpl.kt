package com.ggd.repository

import com.ggd.mapper.toEntity
import com.ggd.mapper.toModel
import com.ggd.model.login.requests.LoginDto
import com.ggd.model.login.requests.TokenDto
import com.ggd.model.login.response.Login
import com.ggd.model.login.response.RefreshToken
import com.ggd.model.login.response.Token
import com.ggd.network.api.LoginApi
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val authApi: LoginApi
) : LoginRepository {

    override suspend fun login(loginDto: LoginDto): Login =
        authApi.login(loginDto.toModel()).data.toEntity()

    override suspend fun refreshToken(): RefreshToken =
        authApi.refreshToken().data.toEntity()

    override suspend fun token(tokenDto: TokenDto): Token =
        authApi.token(tokenDto.toModel()).data.toEntity()

//    override suspend fun register(registerDto: RegisterDto) =
//        authApi.register(registerDto.toModel()).data

//    override suspend fun getUser(): User =
//        authApi.getProfile().data.toEntity()

}