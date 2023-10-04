package com.ggd.network.response.email

data class EmailResponse(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: String
)