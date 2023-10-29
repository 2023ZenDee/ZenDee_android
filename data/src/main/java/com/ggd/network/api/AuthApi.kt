package com.ggd.network.api

import com.ggd.network.request.auth.LoginRequest
import com.ggd.network.request.auth.RegisterRequest
import com.ggd.network.response.IssueResponse
import com.ggd.network.response.auth.LoginResponse
import com.ggd.network.response.auth.RefreshTokenResponseDto
import com.ggd.network.response.auth.RegisterResponse
import com.ggd.network.response.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {

    @POST("/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @POST("/auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): RegisterResponse

    @POST("/auth/refreshToken")
    suspend fun getAccessToken(
        @Header("refreshToken") refreshToken: String
    ): Response<RefreshTokenResponseDto>

}
