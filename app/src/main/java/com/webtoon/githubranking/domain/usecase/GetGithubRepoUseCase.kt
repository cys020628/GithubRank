package com.webtoon.githubranking.domain.usecase

import androidx.paging.PagingData
import com.webtoon.githubranking.domain.model.GithubRepoModel
import com.webtoon.githubranking.domain.repository.GithubRepoListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGithubRepoUseCase @Inject constructor(
    private val repository: GithubRepoListRepository
) {
    operator fun invoke(
        query: String,
        sort: String
    ): Flow<PagingData<GithubRepoModel>> = repository.getGithubRepo(query, sort)
}