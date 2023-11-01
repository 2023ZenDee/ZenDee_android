package com.ggd.network.response.user

import com.ggd.model.user.ContentData

data class MyLikeContentResponseDto(
    val status: Int,
    val success: Boolean,
    val message : String,
    val data : List<ContentData>
)
