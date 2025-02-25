package com.webtoon.githubranking.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.webtoon.githubranking.data.paging.GithubReposPagingSource
import com.webtoon.githubranking.data.remote.SearchGithubRepositoryApi
import com.webtoon.githubranking.domain.model.GithubRepoModel
import com.webtoon.githubranking.domain.repository.GithubRepoListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepoListRepositoryImpl @Inject constructor(
    private val api: SearchGithubRepositoryApi
) : GithubRepoListRepository {

    override fun getGithubRepo(query: String, sort: String): Flow<PagingData<GithubRepoModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GithubReposPagingSource(
                    api = api,
                    query = query,
                    sort = sort
                )
            }
        ).flow
    }
}