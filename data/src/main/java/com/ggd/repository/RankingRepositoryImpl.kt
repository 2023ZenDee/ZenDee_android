package com.ggd.repository

import androidx.paging.Config
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.ggd.datasource.RankingPagingSource
import com.ggd.model.Issue.IssueModel
import com.ggd.network.api.IssueService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RankingRepositoryImpl @Inject constructor(
    private val issueService: IssueService
) {

    var sortBy = "views"
    var tags = listOf<String>("경고","뜨거움","재미","행운","공지","활동","사랑")

    var pagingSource = RankingPagingSource(issueService,sortBy,tags)

    fun getRank() = Pager(
            config = PagingConfig(pageSize = 15, enablePlaceholders = false),
            pagingSourceFactory = {
                pagingSource
            }
        ).liveData

    fun updateFilter(sortBy: String, tags: List<String>){
        this.sortBy = sortBy
        this.tags = tags
        pagingSource = RankingPagingSource(issueService,sortBy,tags)
    }

}