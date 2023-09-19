package com.ggd.model

data class LikeModel(

    val likeIdx : Int,
    val likesBad : Boolean,
    val posterIdx : Int,
    val authorIdx : Int,
    val likescount : Int,
    val badscount : Int

)
