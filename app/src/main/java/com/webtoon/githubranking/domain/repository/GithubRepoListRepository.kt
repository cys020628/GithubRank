package com.webtoon.githubranking.domain.repository

import androidx.paging.PagingData
import com.webtoon.githubranking.domain.model.GithubRepoModel
import kotlinx.coroutines.flow.Flow

interface GithubRepoListRepository {

    fun getGithubRepo(
        query: String,
        sort: String
    ): Flow<PagingData<GithubRepoModel>>
}