package com.ggd.network.api

import com.ggd.network.request.login.LoginRequest
import com.ggd.network.response.Response
import com.ggd.network.response.login.LoginResponse
import com.ggd.network.response.login.RefreshTokenResponse
import com.ggd.network.response.login.TokenResponse
import com.ggd.network.request.login.RegisterRequest
import kr.hs.dgsw.woowasiblings.dgswchat.data.network.request.auth.TokenRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface LoginApi {

    @POST("auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): Response<LoginResponse>

    @GET("auth/refreshToken")
    suspend fun refreshToken(
    ): Response<RefreshTokenResponse>

    @POST("auth/token")
    suspend fun token(
        @Body tokenRequest: TokenRequest
    ): Response<TokenResponse>

    @POST("auth/register")
    suspend fun register(
        @Body registerRequest: RegisterRequest
    ): Response<Unit>

//    @GET("auth/myProfile")
//    suspend fun getProfile(
//    ): Response<UserResponse>
}
