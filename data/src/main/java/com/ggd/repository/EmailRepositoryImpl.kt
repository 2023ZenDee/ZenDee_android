package com.ggd.repository

import com.ggd.mapper.toDto
import com.ggd.mapper.toModel
import com.ggd.model.email.EmailRequestModel
import com.ggd.model.email.EmailResponseModel
import com.ggd.network.api.EmailApi
import javax.inject.Inject

class EmailRepositoryImpl @Inject constructor(
    private val emailApi: EmailApi
): EmailRepository {

    override suspend fun deliverEmail(emailRequestModel: EmailRequestModel): EmailResponseModel
        = emailApi.deliverEmail(emailRequestModel.toDto()).toModel()

}