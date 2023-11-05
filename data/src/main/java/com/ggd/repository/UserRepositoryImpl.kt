package com.ggd.repository

import com.ggd.mapper.toModel
import com.ggd.model.user.FixedInfo
import com.ggd.model.user.FixedInfoResponseModel
import com.ggd.model.user.MyCommentContentResponseModel
import com.ggd.model.user.MyInfoResponseModel
import com.ggd.model.user.MyIssueContentResponseModel
import com.ggd.model.user.MyLikeContentResponseModel
import com.ggd.model.user.MyUnLikeContentResponseModel
import com.ggd.network.api.UserApi
import okhttp3.MultipartBody
import java.io.File
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
): UserRepository {

    override suspend fun getMyInfo(): MyInfoResponseModel =
        userApi.getMyInfo().toModel()

    override suspend fun editMyImage(img: MultipartBody.Part?): FixedInfoResponseModel =
        userApi.editMyImage(img)

    override suspend fun getMyLikeContent(): MyLikeContentResponseModel =
        userApi.getMyLikeContent().toModel()

    override suspend fun getMyUnLikeContent(): MyUnLikeContentResponseModel =
        userApi.getMyUnLikeContent().toModel()

    override suspend fun getMyIssueContent(): MyIssueContentResponseModel =
        userApi.getMyIssueContent().toModel()

    override suspend fun getMyCommentContent(): MyCommentContentResponseModel =
        userApi.getMyCommentContent().toModel()

}