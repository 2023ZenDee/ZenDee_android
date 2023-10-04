package com.ggd.network.api

import com.ggd.network.request.FixIssueRequest
import com.ggd.network.response.IssueResponse
import com.ggd.utils.base.BaseResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface IssueService {

    @Multipart
    @POST("/issues")
    suspend fun postIssue(
        @Part("title") title : RequestBody,
        @Part("content") content : RequestBody,
        @Part("lat") lat : Float,
        @Part("lng") lng : Float,
        @Part("tag") tag : RequestBody,
        @Part("deleted_at") deleted_at : Int,
        @Part img : MultipartBody.Part?
    ) : BaseResponse<Any>

    @GET("/issues/mymap")
    suspend fun getIssuesByLocation(
        @Query("lat") latitude : Float,
        @Query("lng") longitude : Float
    ) : BaseResponse<List<IssueResponse>>

    @GET("/issues/board/{id}")
    suspend fun getIssues(
        @Path("id") id : Int
    ) : BaseResponse<IssueResponse>

    @PATCH("/issues/fix/{id}")
    suspend fun fixIssue(
        @Path("id") id : Int,
        @Body issue: FixIssueRequest
    ) : BaseResponse<IssueResponse>

    @DELETE("issues/fire/{id}")
    suspend fun deleteIssue(
        @Path("id") id : Int,
    ) : BaseResponse<Any>


}