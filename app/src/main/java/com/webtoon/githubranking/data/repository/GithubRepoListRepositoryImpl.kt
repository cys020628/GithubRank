package com.webtoon.githubranking.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.webtoon.githubranking.BuildConfig
import com.webtoon.githubranking.data.local.db.GithubRepoDao
import com.webtoon.githubranking.data.local.db.GithubRepoEntity
import com.webtoon.githubranking.data.mapper.toEntity
import com.webtoon.githubranking.data.mapper.toModel
import com.webtoon.githubranking.data.remote.SearchGithubRepositoryApi
import com.webtoon.githubranking.domain.model.GithubRepoModel
import com.webtoon.githubranking.domain.repository.GithubRepoListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepoListRepositoryImpl @Inject constructor(
    private val api: SearchGithubRepositoryApi,
    private val  githubRepoDao: GithubRepoDao
) : GithubRepoListRepository {

    override fun getGitHubRepo(query: String, sort: String): Flow<Result<List<GithubRepoEntity>>> = flow {
        val result = runCatching {
            api.getPopularRepositories(
                authorization = BuildConfig.GITHUB_TOKEN,
                query = query,
                sort = sort
            )
        }

        result.onSuccess { response ->
            val repoEntities = response.repositories.map { it.toEntity() }
            emit(Result.success<List<GithubRepoEntity>>(repoEntities))
        }.onFailure { exception ->
            Timber.e("API 호출 실패: ${exception.message}")
            emit(Result.failure<List<GithubRepoEntity>>(exception))
        }
    }.flowOn(Dispatchers.IO)

    override fun getPagedGithubRepos(): Flow<PagingData<GithubRepoModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { githubRepoDao.getPagingSource() }
        ).flow.map { pagingData ->
            pagingData.map { it.toModel() }
        }
    }

    override fun getAllReposFlow(): Flow<List<GithubRepoModel>> {
        return githubRepoDao.getAllReposFlow().map { entities ->
            entities.map { it.toModel() }
        }
    }
}
