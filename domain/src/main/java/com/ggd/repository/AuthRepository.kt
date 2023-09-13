package com.ggd.repository

import com.ggd.model.auth.LoginRequestModel
import com.ggd.model.auth.LoginResponseModel
import com.ggd.model.auth.RegisterRequestModel
import com.ggd.model.auth.RegisterResponseModel

interface AuthRepository {

    suspend fun login(
        loginRequestModel: LoginRequestModel
    ): LoginResponseModel

    suspend fun register(
        registerRequestModel: RegisterRequestModel
    ): RegisterResponseModel

}