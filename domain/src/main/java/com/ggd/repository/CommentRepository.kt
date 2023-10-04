package com.ggd.repository

import com.ggd.model.Issue.CommentModel
import org.w3c.dom.Comment

interface CommentRepository {

    suspend fun postComment(
        id : Int,
        content : String
    ) : Any?

    suspend fun deleteComment(
        id : Int
    ) : Any?

    suspend fun getComments(
        id : Int
    ) : List<CommentModel>?

    suspend fun fixComment(
        id : Int,
        fixedContent : String
    ) : CommentModel?

}