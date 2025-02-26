package com.webtoon.githubranking.domain.usecase

import androidx.paging.PagingData
import com.webtoon.githubranking.domain.model.GithubRepoModel
import com.webtoon.githubranking.domain.repository.GithubRepoListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPagedGithubReposUseCase @Inject constructor(
    private val repository: GithubRepoListRepository
) {
    operator fun invoke(): Flow<PagingData<GithubRepoModel>> {
        return repository.getPagedGithubRepos()
    }
}
