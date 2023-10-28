package com.ggd.repository

import com.ggd.mapper.toModel
import com.ggd.model.user.MyLikeContentResponseModel
import com.ggd.network.api.UserApi
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi
): UserRepository {

    override suspend fun getMyLikeContent(): MyLikeContentResponseModel =
        userApi.getMyLikeContent().toModel()

}