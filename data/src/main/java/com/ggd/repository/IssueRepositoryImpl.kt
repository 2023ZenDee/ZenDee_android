package com.ggd.repository

import android.util.Log
import com.ggd.mapper.toModel
import com.ggd.mapper.toRequest
import com.ggd.model.Issue.FixIssueDto
import com.ggd.model.Issue.IssueModel
import com.ggd.model.Issue.PostIssueDto
import com.ggd.network.api.IssueService
import com.ggd.utils.zendeeApiCall
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IssueRepositoryImpl @Inject constructor(

    private val issueService: IssueService

    ) : IssueRepository {

    override suspend fun postIssue(issue: PostIssueDto): Any? {

        val response = zendeeApiCall{ issueService.postIssue(issue = issue.toRequest()) }

        Log.d("젠디", "postIssue: ${response.status} - ${response.message} ")

        return response.data
    }

    override suspend fun getIssuesByLocation(lat: Float, lng: Float): List<IssueModel>? {

        val response =  zendeeApiCall{ issueService.getIssuesByLocation(latitude = lat, longitude =  lng) }

        Log.d("젠디", "getIssuesByLocation: ${response.status} - ${response.message} ")

        return response.data?.map { it.toModel() }
    }

    override suspend fun getIssues(id: Int): List<IssueModel>? {

        val response =  zendeeApiCall{ issueService.getIssues(id) }

        Log.d("젠디", "getIssues: ${response.status} - ${response.message} ")

        return response.data?.map { it.toModel() }
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