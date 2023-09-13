package com.ggd.mapper

import com.ggd.model.email.EmailRequestModel
import com.ggd.model.email.EmailResponseModel
import com.ggd.network.request.email.EmailRequest
import com.ggd.network.response.email.EmailResponse

fun EmailRequestModel.toDto() = EmailRequest(
    email = this.email
)

fun EmailResponse.toModel() = EmailResponseModel(
    message = this.message,
    data = this.data
)