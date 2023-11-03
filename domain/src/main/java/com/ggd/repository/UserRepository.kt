package com.ggd.repository

import com.ggd.model.user.FixedInfo
import com.ggd.model.user.FixedInfoResponseModel
import com.ggd.model.user.MyCommentContentResponseModel
import com.ggd.model.user.MyInfoResponseModel
import com.ggd.model.user.MyIssueContentResponseModel
import com.ggd.model.user.MyLikeContentResponseModel
import com.ggd.model.user.MyUnLikeContentResponseModel
import okhttp3.MultipartBody
import java.io.File

interface UserRepository {

    suspend fun getMyInfo(
    ): MyInfoResponseModel

    suspend fun editMyInfo(
        img: MultipartBody.Part?,
        nick: String
    ): FixedInfoResponseModel

    suspend fun getMyLikeContent(
    ): MyLikeContentResponseModel

    suspend fun getMyUnLikeContent(
    ): MyUnLikeContentResponseModel

    suspend fun getMyIssueContent(
    ): MyIssueContentResponseModel

    suspend fun getMyCommentContent(
    ): MyCommentContentResponseModel

}