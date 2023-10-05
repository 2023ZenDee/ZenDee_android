package com.ggd.network.api

import com.ggd.network.request.PostLikeRequest
import com.ggd.network.response.login.LikeResponse
import com.ggd.utils.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface LikeService {

    @POST("/like/{id}")
    suspend fun postLike(
        @Path ("id") id : Int,
        @Body badLikes : PostLikeRequest

    ) : BaseResponse<LikeResponse>

}