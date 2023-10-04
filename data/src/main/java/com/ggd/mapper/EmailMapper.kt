package com.ggd.mapper

import com.ggd.model.email.EmailCheckRequestModel
import com.ggd.model.email.EmailCheckResponseModel
import com.ggd.model.email.EmailRequestModel
import com.ggd.model.email.EmailResponseModel
import com.ggd.network.request.email.EmailCheckRequest
import com.ggd.network.request.email.EmailRequest
import com.ggd.network.response.email.EmailCheckResponse
import com.ggd.network.response.email.EmailResponse

fun EmailRequestModel.toDto() = EmailRequest(
    email = this.email
)

fun EmailResponse.toModel() = EmailResponseModel(
    message = this.message,
    data = this.data
)

fun EmailCheckRequestModel.toDto() = EmailCheckRequest(
    code = this.code
)

fun EmailCheckResponse.toModel() = EmailCheckResponseModel(
    success = this.success
)