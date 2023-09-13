package com.ggd.network.api

import com.ggd.network.request.auth.RegisterRequest
import com.ggd.network.request.email.EmailRequest
import com.ggd.network.response.email.EmailResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailApi {

    @POST("/mail")
    suspend fun deliverEmail(
        @Body emailRequest: EmailRequest
    ): EmailResponse

}