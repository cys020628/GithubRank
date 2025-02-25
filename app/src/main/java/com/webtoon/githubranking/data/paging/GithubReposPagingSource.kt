package com.webtoon.githubranking.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.webtoon.githubranking.BuildConfig
import com.webtoon.githubranking.data.local.db.GithubRepoDao
import com.webtoon.githubranking.data.local.preferences.PreferenceManager
import com.webtoon.githubranking.data.mapper.toEntity
import com.webtoon.githubranking.data.mapper.toModel
import com.webtoon.githubranking.data.remote.SearchGithubRepositoryApi
import com.webtoon.githubranking.domain.model.GithubRepoModel
import kotlinx.coroutines.flow.first

class GithubReposPagingSource(
    private val api: SearchGithubRepositoryApi,
    private val query: String,
    private val sort: String,
    private val githubRepoDao: GithubRepoDao,
    private val preferenceManager: PreferenceManager
) : PagingSource<Int, GithubRepoModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepoModel> {
        return try {
            val page = params.key ?: 1
            val currentTime = System.currentTimeMillis()
            val lastUpdated = preferenceManager.getLastUpdatedTime()
            val oneDayMillis = 24 * 60 * 60 * 1000

            if (currentTime - lastUpdated < oneDayMillis) {
                val cachedData = githubRepoDao.getAllRepos().first()
                val convertedData = cachedData.map { it.toModel() }

                return LoadResult.Page(
                    data = convertedData,
                    prevKey = null,
                    nextKey = null
                )
            }



            val response = api.getPopularRepositories(
                authorization = "${BuildConfig.GITHUB_TOKEN}",
                query = query,
                sort = sort
            )

            if (response.repositories.isNotEmpty()) {
                val repoEntities = response.repositories.map { it.toEntity() }
                githubRepoDao.clearRepos()
                githubRepoDao.insertRepos(repoEntities)
                preferenceManager.setLastUpdatedTime(currentTime)
            }

            LoadResult.Page(
                data = response.repositories.map { it.toModel() },
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.repositories.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GithubRepoModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}