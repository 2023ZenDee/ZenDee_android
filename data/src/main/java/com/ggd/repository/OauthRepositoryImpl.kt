package com.ggd.repository

import com.ggd.mapper.toDto
import com.ggd.mapper.toModel
import com.ggd.model.oauth.GoogleOauthRequestModel
import com.ggd.model.oauth.GoogleOauthResponseModel
import com.ggd.network.api.OauthApi
import javax.inject.Inject

class OauthRepositoryImpl @Inject constructor(
    private val oauthApi: OauthApi
): OauthRepository {

    override suspend fun googleOauthLogin(googleOauthRequestModel: GoogleOauthRequestModel): GoogleOauthResponseModel
        = oauthApi.googleOauthLogin(googleOauthRequestModel.toDto()).toModel()

}