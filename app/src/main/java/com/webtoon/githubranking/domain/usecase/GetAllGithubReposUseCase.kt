package com.webtoon.githubranking.domain.usecase

import com.webtoon.githubranking.domain.model.GithubRepoModel
import com.webtoon.githubranking.domain.repository.GithubRepoListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllGithubReposUseCase @Inject constructor(
    private val repository: GithubRepoListRepository
) {
    operator fun invoke(): Flow<List<GithubRepoModel>> {
        return repository.getAllReposFlow()
    }
}