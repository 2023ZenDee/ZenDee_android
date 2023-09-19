package com.ggd.repository

import android.util.Log
import com.ggd.mapper.toModel
import com.ggd.mapper.toRequest
import com.ggd.model.Issue.FixIssueDto
import com.ggd.model.Issue.IssueModel
import com.ggd.model.Issue.PostIssueDto
import com.ggd.network.api.IssueService
import com.ggd.utils.zendeeApiCall
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IssueRepositoryImpl @Inject constructor(

    private val issueService: IssueService

    ) : IssueRepository {

    override suspend fun postIssue(issue: PostIssueDto): Any? {

        val response = zendeeApiCall{ issueService.postIssue(
            title = issue.title.toRequestBody("text/plain".toMediaTypeOrNull()),
            content = issue.content.toRequestBody("text/plain".toMediaTypeOrNull()),
            img = issue.postImg,
            lat = issue.lat,
            lng = issue.lng,
            tag = issue.tag.toRequestBody("text/plain".toMediaTypeOrNull()),
            deleted_at = issue.deleted_at
        ) }

        Log.d("젠디", "postIssue: ${response.status} - ${response.message} ")

        return response.data
    }

    override suspend fun getIssuesByLocation(lat: Float, lng: Float): List<IssueModel>? {

        val response =  zendeeApiCall{ issueService.getIssuesByLocation(latitude = lat, longitude =  lng) }

        Log.d("젠디", "getIssuesByLocation: ${response.status} - ${response.message} ")

        return response.data?.map { it.toModel() }

//        val dummies = listOf<IssueModel>(
//            IssueModel(0,"이게 되네 ㅋㅋ","엄 이게 되네","","",35.823413F,128.636297F,"",0,"",0,"경고","예리ㅣㅁ",2,3,4,"대구광역시 구지로 구지면"),
//            IssueModel(0,"예리미는 왜 이쁠까","예리미기 떄문이지!","","",35.824413F,128.632297F,"",0,"",0,"사랑","예리ㅣㅁ",2,3,4,"대구광역시 구지로 구지면"),
//            IssueModel(0,"히히헤헤","에휴","","",35.821413F,128.639297F,"",0,"https://i.ibb.co/ZfZd935/Clipboard-Image-2023-05-18-122422.png",0,"행운","예리ㅣㅁ",2,3,4,"대구광역시 구지로 구지면"),
//            IssueModel(0,"예리미 보구싶다","보고싶다아아","","",35.66796118724206F,128.41695105837402F,"",0,"",0,"행운","예리ㅣㅁ",2,3,4,"대구광역시 구지로 구지면"),
//            IssueModel(0,"언제오냥 김예리무","헤헤","","",35.65796118724206F,128.41395105837402F,"",0,"",0,"재미","예리ㅣㅁ",2,3,4,"대구광역시 구지로 구지면"),
//            IssueModel(0,"사랑해","나두 사랑해","","",35.66296118724206F,128.41995105837402F,"",0,"",0,"사랑","예리ㅣㅁ",2,3,4,"대구광역시 구지로 구지면"),
//
//        )
//
//    return dummies
    }

    override suspend fun getIssues(id: Int): IssueModel? {

        val response =  zendeeApiCall{ issueService.getIssues(id) }

        Log.d("젠디", "getIssues: ${response.status} - ${response.message} ")

        return response.data?.toModel()
    }

    override suspend fun fixIssue(id: Int, issue: FixIssueDto): IssueModel? {

        val response = zendeeApiCall{ issueService.fixIssue(id = id, issue = issue.toRequest()) }

        Log.d("젠디", "fixIssue: ${response.status} - ${response.message} ")

        return response.data?.toModel()
    }

    override suspend fun deleteIssue(id: Int): Any? {
        
        val response = zendeeApiCall{ issueService.deleteIssue(id) }

        Log.d("젠디", "deleteIssue: ${response.status} - ${response.message} ")

        return response.data
    }

}