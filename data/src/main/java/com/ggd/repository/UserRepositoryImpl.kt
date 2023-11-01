package com.ggd.repository

import com.ggd.mapper.toModel
import com.ggd.model.user.MyCommentContentResponseModel
import com.ggd.model.user.MyIssueContentResponseModel
import com.ggd.model.user.MyLikeContentResponseModel
import com.ggd.model.user.MyUnLikeContentResponseModel
import com.ggd.network.api.UserApi
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
): UserRepository {

    override suspend fun getMyLikeContent(): MyLikeContentResponseModel =
        userApi.getMyLikeContent().toModel()

    override suspend fun getMyUnLikeContent(): MyUnLikeContentResponseModel =
        userApi.getMyUnLikeContent().toModel()

    override suspend fun getMyIssueContent(): MyIssueContentResponseModel =
        userApi.getMyIssueContent().toModel()

    override suspend fun getMyCommentContent(): MyCommentContentResponseModel =
        userApi.getMyCommentContent().toModel()

}