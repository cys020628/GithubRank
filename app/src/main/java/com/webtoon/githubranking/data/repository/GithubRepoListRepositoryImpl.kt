package com.webtoon.githubranking.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.webtoon.githubranking.data.local.db.GithubRepoDao
import com.webtoon.githubranking.data.local.preferences.PreferenceManager
import com.webtoon.githubranking.data.paging.GithubReposPagingSource
import com.webtoon.githubranking.data.remote.SearchGithubRepositoryApi
import com.webtoon.githubranking.domain.model.GithubRepoModel
import com.webtoon.githubranking.domain.repository.GithubRepoListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepoListRepositoryImpl @Inject constructor(
    private val api: SearchGithubRepositoryApi,
    private val githubRepoDao: GithubRepoDao, // ✅ Room DAO 추가
    private val preferenceManager: PreferenceManager // ✅ SharedPreferences 추가
) : GithubRepoListRepository {

    override fun getGithubRepo(query: String, sort: String): Flow<PagingData<GithubRepoModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false,
                maxSize = 200
            ),
            pagingSourceFactory = {
                GithubReposPagingSource(
                    api = api,
                    query = query,
                    sort = sort,
                    githubRepoDao = githubRepoDao,
                    preferenceManager = preferenceManager
                )
            }
        ).flow
    }
}
