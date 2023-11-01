package com.ggd.network.api

import com.ggd.model.user.MyCommentContentResponseModel
import com.ggd.model.user.MyIssueContentResponseModel
import com.ggd.network.response.user.MyCommentContentResponseDto
import com.ggd.network.response.user.MyIssueContentResponseDto
import com.ggd.network.response.user.MyLikeContentResponseDto
import com.ggd.network.response.user.MyUnLikeContentResponseDto
import retrofit2.http.GET

interface UserApi {

    @GET("/auth/my/like")
    suspend fun getMyLikeContent(
    ): MyLikeContentResponseDto

    @GET("/auth/my/bad")
    suspend fun getMyUnLikeContent(
    ): MyUnLikeContentResponseDto

    @GET("/auth/my/issue")
    suspend fun getMyIssueContent(
    ): MyIssueContentResponseDto

    @GET("/auth/my/comment")
    suspend fun getMyCommentContent(
    ): MyCommentContentResponseDto

}