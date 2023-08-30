package com.ggd.repository

import com.ggd.model.Issue.CommentModel
import org.w3c.dom.Comment

interface CommentRepository {

    suspend fun postComment(
        id : Int
    ) : Any?

    suspend fun deleteComment(
        id : Int
    ) : Any?

    suspend fun getComments(
        id : Int
    ) : CommentModel?

    suspend fun fixComment(
        id : Int,
        fixedContent : String
    ) : CommentModel?

}