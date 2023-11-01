package com.ggd.mapper

import com.ggd.model.oauth.GoogleOauthRequestModel
import com.ggd.model.oauth.GoogleOauthResponseModel
import com.ggd.network.request.oauth.GoogleOauthRequestDto
import com.ggd.network.response.oauth.GoogleOauthResponseDto

fun GoogleOauthRequestModel.toDto() = GoogleOauthRequestDto(
    email = this.email,
    nick = this.nick
)

fun GoogleOauthResponseDto.toModel() = GoogleOauthResponseModel(
    accessToken = this.accessToken,
    refreshToken = this.refreshToken
)