package com.ggd.network.api

import com.ggd.network.request.oauth.GoogleOauthRequestDto
import com.ggd.network.response.oauth.GoogleOauthResponseDto
import retrofit2.http.Body
import retrofit2.http.POST

interface OauthApi {

    @POST("/oauth/google")
    suspend fun googleOauthLogin(
        @Body googleOauthRequestDto: GoogleOauthRequestDto
    ): GoogleOauthResponseDto

}