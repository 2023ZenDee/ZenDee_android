package com.ggd.model.Issue

data class PostIssueDto(

    val title : String,
    val content : String,
    val postImg : String,
    val lat : Float,
    val lng : Float,
    val tag : String
)
