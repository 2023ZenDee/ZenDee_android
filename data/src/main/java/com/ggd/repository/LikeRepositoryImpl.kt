package com.ggd.repository

import android.util.Log
import com.ggd.mapper.toModel
import com.ggd.model.LikeModel
import com.ggd.network.api.LikeService
import com.ggd.network.request.PostLikeRequest
import com.ggd.utils.zendeeApiCall
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject

class LikeRepositoryImpl @Inject constructor(

    private val likeService : LikeService

): LikeRepository{

    override suspend fun postLike(id: Int, badLikes: Boolean): LikeModel {

        val response = zendeeApiCall{ likeService.postLike( id, PostLikeRequest(badLikes) ) }

        Log.d("젠디", "postIssue: ${response.status} - ${response.message} ")

        return response.data!!.toModel()
    }



}