package com.ggd.model.Issue

data class CommentModel(

    val cmtIdx : String,
    val cmtContent : String,
    val created_at : String,
    val deleted_at : String?,
    val authorIdx : Int,
    val posterIdx : Int,
    val user : String,
    val userImg : String

)
