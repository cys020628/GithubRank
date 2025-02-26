package com.webtoon.githubranking.domain.repository

import androidx.paging.PagingData
import com.webtoon.githubranking.data.local.db.GithubRepoEntity
import com.webtoon.githubranking.domain.model.GithubRepoModel
import kotlinx.coroutines.flow.Flow

interface GithubRepoListRepository {

    fun getGitHubRepo(
        query: String,
        sort: String
    ):Flow<Result<List<GithubRepoEntity>>>

    fun getPagedGithubRepos(): Flow<PagingData<GithubRepoModel>>

    fun getAllReposFlow():Flow<List<GithubRepoModel>>
}