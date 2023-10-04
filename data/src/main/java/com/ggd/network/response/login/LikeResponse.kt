package com.ggd.network.response.login

data class LikeResponse(

    val likeIdx : Int,
    val likesBad : Boolean,
    val posterIdx : Int,
    val authorIdx : Int,
    val likescount : Int,
    val badscount : Int

)
