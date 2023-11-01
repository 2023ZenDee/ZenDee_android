package com.ggd.mapper

import com.ggd.model.Issue.FixIssueDto
import com.ggd.model.Issue.IssueModel
import com.ggd.model.Issue.PostIssueDto
import com.ggd.network.request.FixIssueRequest
//import com.ggd.network.request.PostIssueRequest
import com.ggd.network.response.IssueResponse

internal fun IssueResponse.toModel() = IssueModel(

    postIdx = postIdx,
    title = title,
    content = content,
    created_at = created_at,
    updated_at = updated_at,
    latitude = latitude,
    longitude = longitude,
    deleted_at = deleted_at,
    authorIdx = authorIdx,
    postImg = postImg,
    views = views,
    tags = tags,
    user = user,
    likes = likes,
    bads = bads,
    comments = comments,
    address = address,
    userLikesPost = userLikesPost

)

//internal fun PostIssueDto.toRequest() = PostIssueRequest(
//    title = title,
//    content = content,
//    postImg = postImg,
//    lat = lat,
//    lng = lng,
//    tag = tag
//)


internal fun FixIssueDto.toRequest() = FixIssueRequest(
    title = title,
    content = content,
    postImg = postImg
)