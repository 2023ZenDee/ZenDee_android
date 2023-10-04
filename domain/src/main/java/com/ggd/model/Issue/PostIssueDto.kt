package com.ggd.model.Issue

import okhttp3.MultipartBody

data class PostIssueDto(

    val title : String,
    val content : String,
    val postImg : MultipartBody.Part?,
    val lat : Float,
    val lng : Float,
    val tag : String,
    val deleted_at : Int
)
