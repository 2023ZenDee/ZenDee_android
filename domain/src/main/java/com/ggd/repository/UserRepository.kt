package com.ggd.repository

import com.ggd.model.user.MyCommentContentResponseModel
import com.ggd.model.user.MyIssueContentResponseModel
import com.ggd.model.user.MyLikeContentResponseModel
import com.ggd.model.user.MyUnLikeContentResponseModel

interface UserRepository {

    suspend fun getMyLikeContent(
    ): MyLikeContentResponseModel

    suspend fun getMyUnLikeContent(
    ): MyUnLikeContentResponseModel

    suspend fun getMyIssueContent(
    ): MyIssueContentResponseModel

    suspend fun getMyCommentContent(
    ): MyCommentContentResponseModel

}