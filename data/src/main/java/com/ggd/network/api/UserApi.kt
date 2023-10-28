package com.ggd.network.api

import com.ggd.network.response.user.MyLikeContentResponseDto
import retrofit2.http.GET

interface UserApi {

    @GET("/auth/my/like")
    suspend fun getMyLikeContent(
    ): MyLikeContentResponseDto

//    @GET("/auth/my/bad")
//    suspend fun getMyLikeContent(
//
//    ): MyLikeContentResponseDto
//
//    @GET("/auth/my/issue")
//    suspend fun getMyLikeContent(
//
//    ): MyLikeContentResponseDto
//
//    @GET("/auth/my/comment")
//    suspend fun getMyLikeContent(
//
//    ): MyLikeContentResponseDto

}