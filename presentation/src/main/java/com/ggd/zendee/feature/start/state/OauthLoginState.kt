package com.ggd.zendee.feature.start.state

data class OauthLoginState(
    val isSuccess: Boolean = false,
    val error: String = ""
)