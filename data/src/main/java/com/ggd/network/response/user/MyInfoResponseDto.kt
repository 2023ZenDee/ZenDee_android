package com.ggd.network.response.user

import com.ggd.model.user.MyInfo

data class MyInfoResponseDto(
    val status: Int,
    val success: Boolean,
    val message : String,
    val data: MyInfo
)
