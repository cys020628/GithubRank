package com.webtoon.githubranking.domain.usecase

import com.webtoon.githubranking.domain.model.GithubOwnerModel
import com.webtoon.githubranking.domain.repository.GithubUserInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserInfoUseCase @Inject constructor(
    private val repository: GithubUserInfoRepository
){

    operator fun invoke(userName:String): Flow<Result<List<GithubOwnerModel>>> = repository.getUserInfo(userName)
}