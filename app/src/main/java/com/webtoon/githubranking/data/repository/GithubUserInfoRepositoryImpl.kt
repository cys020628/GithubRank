package com.webtoon.githubranking.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.webtoon.githubranking.data.mapper.toModel
import com.webtoon.githubranking.data.remote.SearchGithubUserApi
import com.webtoon.githubranking.domain.model.GithubOwnerModel
import com.webtoon.githubranking.domain.repository.GithubUserInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GithubUserInfoRepositoryImpl @Inject constructor(
    private val api: SearchGithubUserApi,
) : GithubUserInfoRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getUserInfo(userName: String): Flow<Result<List<GithubOwnerModel>>> = flow {
        val result = runCatching {
            api.getUserRepositories(userName)
        }
        result
            .onSuccess { response ->
                val userDataListModel = response.map { it.toModel() }
                emit(Result.success<List<GithubOwnerModel>>(userDataListModel))
            }
            .onFailure { exception ->
                emit(Result.failure<List<GithubOwnerModel>>(exception))
            }
    }.flowOn(Dispatchers.IO)
}