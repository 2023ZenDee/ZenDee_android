package com.ggd.network.api

import com.ggd.model.user.FixedInfo
import com.ggd.model.user.FixedInfoResponseModel
import com.ggd.model.user.MyCommentContentResponseModel
import com.ggd.model.user.MyIssueContentResponseModel
import com.ggd.network.response.user.MyCommentContentResponseDto
import com.ggd.network.response.user.MyInfoResponseDto
import com.ggd.network.response.user.MyIssueContentResponseDto
import com.ggd.network.response.user.MyLikeContentResponseDto
import com.ggd.network.response.user.MyUnLikeContentResponseDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.Part
import java.io.File

interface UserApi {

    @GET("/auth/my")
    suspend fun getMyInfo(
    ): MyInfoResponseDto

    @Multipart
    @PATCH("/auth/my/fix")
    suspend fun editMyInfo(
        @Part img: MultipartBody.Part?,
        @Part("nick") nick: String
    ): FixedInfoResponseModel

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