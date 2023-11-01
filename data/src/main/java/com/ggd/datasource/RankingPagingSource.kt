package com.ggd.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ggd.mapper.toModel
import com.ggd.model.Issue.IssueModel
import com.ggd.network.api.IssueService
import com.ggd.network.request.RankRequest

class RankingPagingSource(
    private val issueApi : IssueService,
    private val sortBy : String,
    private val tags : List<String>
) :  PagingSource<Int, IssueModel>(){
    override fun getRefreshKey(state: PagingState<Int, IssueModel>): Int? {
//        val lastItem = state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()
//        return lastItem?.postIdx
//        return state.anchorPosition?.let { position ->
//            state.closestPageToPosition(position)?.prevKey?.plus(1)
//                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
//        }
//
        return 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, IssueModel> {
        try {
            val page = params.key ?: 1
            val pageSize = 15

            val response = issueApi.getRank(
                sortBy = sortBy,
                tags = RankRequest(tags),
                page = page,
                pageSize = pageSize
            )

            // LoadResult 반환
            return LoadResult.Page(
                data = response.data!!.map { it.toModel() },
                prevKey = null,
                nextKey = if (response.data.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}