package com.ggd.mapper

import com.ggd.model.LikeModel
import com.ggd.model.login.requests.LoginDto
import com.ggd.network.request.login.LoginRequest
import com.ggd.network.response.login.LikeResponse

fun LikeResponse.toModel(): LikeModel = LikeModel(

    authorIdx = this.authorIdx,
    likeIdx = this.likeIdx,
    posterIdx = this.posterIdx,
    likesBad = this.likesBad,
    badscount = this.badscount,
    likescount = this.likescount

)
