package com.webtoon.githubranking.domain.repository

import com.webtoon.githubranking.domain.model.GithubOwnerModel
import kotlinx.coroutines.flow.Flow

interface GithubUserInfoRepository {

    fun getUserInfo(userName:String) : Flow<Result<List<GithubOwnerModel>>>
}