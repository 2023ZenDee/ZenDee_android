package com.ggd.network.api

import com.ggd.network.request.PostCommentRequest
import com.ggd.network.response.CommentResponse
import com.ggd.utils.base.BaseResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface CommentService{

    @POST("/comments/{id}")
    suspend fun postComment(
        @Path("id") id : Int,
        @Body content : PostCommentRequest
    ) : BaseResponse<Any>

    @DELETE("/comments/fire/{id}")
    suspend fun deleteComment(
        @Path("id") id : Int
    ) : BaseResponse<Any>

   @GET("/comments/get/{id}")
   suspend fun getComments(
       @Path("id") id : Int
   ) : BaseResponse<List<CommentResponse>>


    @GET("/comments/fix/{id}")
    suspend fun fixComment(
        @Path("id") id : Int,
        @Body fixedcontent : String
    ) : BaseResponse<CommentResponse>

}

