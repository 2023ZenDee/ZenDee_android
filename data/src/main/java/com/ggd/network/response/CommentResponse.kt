package com.ggd.network.response

data class CommentResponse(

    val cmtIdx : String,
    val cmtContent : String,
    val created_at : String,
    val deleted_at : String,
    val authorIdx : Int,
    val posterIdx : Int,

)