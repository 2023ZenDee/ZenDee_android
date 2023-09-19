package com.ggd.mapper

import com.ggd.model.Issue.CommentModel
import com.ggd.network.api.CommentService
import com.ggd.network.response.CommentResponse

internal fun CommentResponse.toModel() = CommentModel(

    cmtIdx = this.cmtIdx,
    cmtContent = this.cmtContent,
    created_at = this.created_at,
    deleted_at = this.deleted_at,
    authorIdx = this.authorIdx,
    posterIdx = this.posterIdx,
    user = this.user,
    userImg = this.userImg

)