package com.ggd.repository

import android.util.Log
import com.ggd.mapper.toDto
import com.ggd.mapper.toModel
import com.ggd.model.auth.LoginRequestModel
import com.ggd.model.auth.LoginResponseModel
import com.ggd.model.auth.RefreshTokenResponseModel
import com.ggd.model.auth.RegisterRequestModel
import com.ggd.model.auth.RegisterResponseModel
import com.ggd.network.api.AuthApi
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRepository {

    override suspend fun login(loginRequestModel: LoginRequestModel): LoginResponseModel
        = authApi.login(loginRequestModel.toDto()).toModel()

    override suspend fun register(registerRequestModel: RegisterRequestModel): RegisterResponseModel
        = authApi.register(registerRequestModel.toDto()).toModel()

    override suspend fun getAccessToken(refreshToken: String): RefreshTokenResponseModel =
        authApi.getAccessToken(refreshToken).data.toModel()

}