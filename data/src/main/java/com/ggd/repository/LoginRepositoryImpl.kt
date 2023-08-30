package com.ggd.repository

import com.ggd.mapper.toEntity
import com.ggd.mapper.toModel
import com.ggd.model.login.Login
import com.ggd.model.login.LoginDto
import com.ggd.model.login.RefreshToken
import com.ggd.model.login.Token
import com.ggd.model.login.TokenDto
import com.ggd.network.api.LoginApi
import dagger.hilt.android.AndroidEntryPoint
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