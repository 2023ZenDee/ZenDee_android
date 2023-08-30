package com.ggd.network.api

import com.ggd.network.request.FixIssueRequest
import com.ggd.network.request.PostIssueRequest
import com.ggd.network.response.IssueResponse
import com.ggd.utils.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface IssueService {

    @POST("/issues")
    suspend fun postIssue(
        @Body issue :PostIssueRequest

    ) : BaseResponse<Any>

    @GET("/issues/mymap")
    suspend fun getIssuesByLocation(
        @Query("lat") latitude : Float,
        @Query("lng") longitude : Float
    ) : BaseResponse<List<IssueResponse>>

    @GET("/issues/board/{id}")
    suspend fun getIssues(
        @Path("id") id : Int
    ) : BaseResponse<List<IssueResponse>>

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