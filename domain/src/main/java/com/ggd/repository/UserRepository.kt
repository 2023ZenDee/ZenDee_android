package com.ggd.repository

import com.ggd.model.user.MyLikeContentResponseModel

interface UserRepository {

    suspend fun getMyLikeContent(
    ): MyLikeContentResponseModel

}