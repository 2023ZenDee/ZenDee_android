package com.ggd.repository

import android.util.Log
import com.ggd.mapper.toEntity
import com.ggd.mapper.toModel
import com.ggd.model.login.requests.LoginDto
import com.ggd.model.login.response.Login
import com.ggd.network.api.LoginApi
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val authApi: LoginApi
) : LoginRepository {

    override suspend fun login(loginDto: LoginDto): Login {
        val res = authApi.login(loginDto.toModel())
        Log.d("ErrorTest", res.toString())
        return res.toEntity()
    }



}