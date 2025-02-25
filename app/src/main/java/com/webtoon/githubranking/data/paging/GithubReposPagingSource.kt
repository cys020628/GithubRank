package com.webtoon.githubranking.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.webtoon.githubranking.data.mapper.toModel
import com.webtoon.githubranking.data.remote.SearchGithubRepositoryApi
import com.webtoon.githubranking.domain.model.GithubRepoModel

class GithubReposPagingSource(
    private val api: SearchGithubRepositoryApi,
    private val query: String,
    private val sort: String
) : PagingSource<Int, GithubRepoModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepoModel> {
        val page = params.key ?: 1
        val response = api.getPopularRepositories(query, sort)

        return if (response.repositories.isNotEmpty()) {
            LoadResult.Page(
                data = response.repositories.map { it.toModel() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.repositories.isEmpty()) null else page + 1
            )
        } else {
            LoadResult.Error(Exception("Empty Github Repository"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GithubRepoModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}