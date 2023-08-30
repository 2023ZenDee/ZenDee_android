package com.ggd.repository

import com.ggd.model.Issue.FixIssueDto
import com.ggd.model.Issue.IssueModel
import com.ggd.model.Issue.PostIssueDto

interface IssueRepository {

    suspend fun postIssue(
        issue : PostIssueDto
    ) : Any?

    suspend fun getIssuesByLocation(
        lat : Float,
        lng : Float
    ) : List<IssueModel>?

    suspend fun getIssues(
        id : Int
    ) : List<IssueModel>?

    suspend fun fixIssue(
        id : Int,
        issue : FixIssueDto
    ) : IssueModel?

    suspend fun deleteIssue(
        id : Int
    ) : Any?

}
