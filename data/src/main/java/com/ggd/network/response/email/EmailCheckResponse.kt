package com.ggd.network.response.email

data class EmailCheckResponse(
    val status: Int,
    val success: Boolean,
    val message: String
)