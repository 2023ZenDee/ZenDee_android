package com.ggd.repository

import com.ggd.model.oauth.GoogleOauthRequestModel
import com.ggd.model.oauth.GoogleOauthResponseModel

interface OauthRepository {

    suspend fun googleOauthLogin(
        googleOauthRequestModel: GoogleOauthRequestModel
    ): GoogleOauthResponseModel

}