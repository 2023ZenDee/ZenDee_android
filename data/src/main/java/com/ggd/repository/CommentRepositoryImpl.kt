package com.ggd.repository

import android.util.Log
import com.ggd.mapper.toModel
import com.ggd.model.Issue.CommentModel
import com.ggd.network.api.CommentService
import com.ggd.utils.zendeeApiCall
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentService: CommentService
) : CommentRepository {

    override suspend fun postComment(id: Int): Any? {

        val response = zendeeApiCall { commentService.postComment(id) }

        Log.d("젠디", "postComment: ${response.status} - ${response.message} ")

        return response.data
    }

    override suspend fun deleteComment(id: Int): Any? {

        val response = zendeeApiCall { commentService.deleteComment(id) }

        Log.d("젠디", "deleteComment: ${response.status} - ${response.message} ")

        return response.data
    }

    override suspend fun getComments(id: Int): List<CommentModel>? {

        val response = zendeeApiCall { commentService.getComments(id) }

        Log.d("젠디", "getComments: ${response.status} - ${response.message} ")

        return response.data?.map{ it.toModel() }

    }

    override suspend fun fixComment(id: Int, fixedContent: String): CommentModel? {

        val response = zendeeApiCall { commentService.fixComment(id, fixedContent) }

        Log.d("젠디", "fixComment: ${response.status} - ${response.message} ")

        return response.data?.toModel()

    }


}