package com.ggd.mapper

import com.ggd.model.user.MyCommentContentResponseModel
import com.ggd.model.user.MyIssueContentResponseModel
import com.ggd.model.user.MyLikeContentResponseModel
import com.ggd.model.user.MyUnLikeContentResponseModel
import com.ggd.network.response.user.MyCommentContentResponseDto
import com.ggd.network.response.user.MyIssueContentResponseDto
import com.ggd.network.response.user.MyLikeContentResponseDto
import com.ggd.network.response.user.MyUnLikeContentResponseDto

fun MyCommentContentResponseDto.toModel() = MyCommentContentResponseModel(
    a = this.a
)

fun MyIssueContentResponseDto.toModel() = MyIssueContentResponseModel(
    a = this.a
)

fun MyLikeContentResponseDto.toModel() = MyLikeContentResponseModel(
    status = this.status,
    success = this.success,
    message = this.message,
    data = this.data
)

fun MyUnLikeContentResponseDto.toModel() = MyUnLikeContentResponseModel(
    a = this.a
)
