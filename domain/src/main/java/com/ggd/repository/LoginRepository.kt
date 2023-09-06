package com.ggd.repository

import com.ggd.model.login.requests.LoginDto
import com.ggd.model.login.response.Login

interface LoginRepository {

    suspend fun login(
        loginDto: LoginDto
    ): Login

}