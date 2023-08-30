package com.ggd.network.request

data class PostIssueRequest(

    val title : String,
    val content : String,
    val postImg : String,
    val lat : Float,
    val lng : Float,
    val tag : String
)
