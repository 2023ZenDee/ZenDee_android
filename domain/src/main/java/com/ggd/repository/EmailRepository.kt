package com.ggd.repository

import com.ggd.model.email.EmailCheckRequestModel
import com.ggd.model.email.EmailCheckResponseModel
import com.ggd.model.email.EmailRequestModel
import com.ggd.model.email.EmailResponseModel

interface EmailRepository {

    suspend fun deliverEmail(
        emailRequestModel: EmailRequestModel
    ): EmailResponseModel

    suspend fun checkEmail(
        mailToken: String,
        emailCheckRequestModel: EmailCheckRequestModel
    ): EmailCheckResponseModel

}