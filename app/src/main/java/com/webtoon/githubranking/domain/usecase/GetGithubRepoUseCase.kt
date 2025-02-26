package com.webtoon.githubranking.domain.usecase

import com.webtoon.githubranking.data.local.db.GithubRepoDao
import com.webtoon.githubranking.domain.repository.GithubRepoListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetGithubRepoUseCase @Inject constructor(
    private val repository: GithubRepoListRepository,
    private val githubRepoDao: GithubRepoDao
) {

    operator fun invoke(query: String, sort: String): Flow<Result<Boolean>> = flow {
        repository.getGitHubRepo(query, sort)
            .collect { result ->
                result.onSuccess { repoEntities ->
                    Timber.e("리소스 개수 :  ${repoEntities.size}")
                    githubRepoDao.clearRepos() // 기존 데이터 삭제
                    githubRepoDao.insertRepos(repoEntities) // 새로운 데이터 저장
                    emit(Result.success<Boolean>(true)) // 저장 성공 반환
                }.onFailure { exception ->
                    Timber.e("저장 실패: ${exception.message}")
                    emit(Result.failure<Boolean>(exception)) // 실패 반환
                }
            }
    }.flowOn(Dispatchers.IO)
}