package com.webtoon.githubranking.data.mapper

import com.webtoon.githubranking.data.dto.GithubSearchReposItems
import com.webtoon.githubranking.data.dto.GithubSearchReposOwner
import com.webtoon.githubranking.domain.model.GithubOwnerModel
import com.webtoon.githubranking.domain.model.GithubRepoModel

fun GithubSearchReposItems.toModel(): GithubRepoModel {
    return GithubRepoModel(
        id = this.id,
        name = this.name,
        fullName = this.fullName,
        owner = this.owner.toModel(), // 소유자 정보 변환
        description = this.description,
        stars = this.stargazersCount,
        forks = this.forksCount,
        language = this.language,
        visibility = this.visibility,
        url = this.htmlUrl
    )
}

// ✅ 소유자 변환 함수
fun GithubSearchReposOwner.toModel(): GithubOwnerModel {
    return GithubOwnerModel(
        username = this.username,
        avatarUrl = this.avatarUrl
    )
}