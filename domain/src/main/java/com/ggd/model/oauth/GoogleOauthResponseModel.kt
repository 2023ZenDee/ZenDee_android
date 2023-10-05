package com.ggd.model.oauth

data class GoogleOauthResponseModel(
    val accessToken : String,
    val refreshToken : String
)