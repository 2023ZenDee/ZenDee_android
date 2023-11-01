package com.ggd.network.response

data class Response<T>(
    val status: Int,
    val success: Boolean,
    val message: String,
    val data: T
)