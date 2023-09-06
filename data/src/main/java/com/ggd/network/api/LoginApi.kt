package com.ggd.network.api

import com.ggd.network.request.login.LoginRequest
import com.ggd.network.response.login.LoginResponse
import com.ggd.network.response.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {

    @POST("/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

}
