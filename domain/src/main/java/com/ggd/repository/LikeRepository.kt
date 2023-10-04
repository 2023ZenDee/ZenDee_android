package com.ggd.repository

import com.ggd.model.LikeModel

interface LikeRepository {

    suspend fun postLike(
        id : Int,
        badLikes : Boolean
    ) : LikeModel
}