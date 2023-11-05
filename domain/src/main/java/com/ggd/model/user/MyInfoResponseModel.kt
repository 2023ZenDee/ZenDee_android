package com.ggd.model.user

data class MyInfoResponseModel(
    val status: Int,
    val success: Boolean,
    val message : String,
    val data: MyInfo
)
