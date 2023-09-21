package com.ggd.network.api

import com.ggd.network.request.email.EmailCheckRequest
import com.ggd.network.request.email.EmailRequest
import com.ggd.network.response.email.EmailCheckResponse
import com.ggd.network.response.email.EmailResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface EmailApi {

    @POST("/mail")
    suspend fun deliverEmail(
        @Body emailRequest: EmailRequest
    ): EmailResponse

    @POST("/mail/check")
    suspend fun checkEmail(
        @Header("mailtoken") mailToken: String,
        @Body emailCheckRequest: EmailCheckRequest,
    ): EmailCheckResponse

}