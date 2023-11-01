package com.ggd.model.user

import java.util.Date

data class ContentData(
    val postIdx: Int,
    val title: String,
    val content: String,
    val created_at: Date,
    val updated_at: Date,
    val latitude: Float,
    val longitude: Float,
    val deleted_at: Date,
    val authorIdx: Int,
    val postImg: String,
    val address: String,
    val views: Int
)
