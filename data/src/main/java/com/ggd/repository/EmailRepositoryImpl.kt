package com.ggd.repository

import android.util.Log
import com.ggd.mapper.toDto
import com.ggd.mapper.toModel
import com.ggd.model.email.EmailCheckRequestModel
import com.ggd.model.email.EmailCheckResponseModel
import com.ggd.model.email.EmailRequestModel
import com.ggd.model.email.EmailResponseModel
import com.ggd.network.api.EmailApi
import javax.inject.Inject

class EmailRepositoryImpl @Inject constructor(
    private val emailApi: EmailApi
): EmailRepository {

    override suspend fun deliverEmail(emailRequestModel: EmailRequestModel): EmailResponseModel
        = emailApi.deliverEmail(emailRequestModel.toDto()).toModel()

    override suspend fun checkEmail(mailToken: String, emailCheckRequestModel: EmailCheckRequestModel): EmailCheckResponseModel {
        return emailApi.checkEmail(mailToken, emailCheckRequestModel.toDto()).toModel()
    }

}