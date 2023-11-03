package com.ggd.model.user

data class FixedInfoResponseModel(
    val status: Int,
    val success: Boolean,
    val message : String,
    val data: MyInfoExceptForEmail
)
