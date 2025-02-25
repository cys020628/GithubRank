package com.webtoon.githubranking.data.mapper

import com.webtoon.githubranking.data.dto.GithubSearchReposItems
import com.webtoon.githubranking.data.dto.GithubSearchReposOwner
import com.webtoon.githubranking.data.local.db.GithubRepoEntity
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

fun GithubSearchReposItems.toEntity(): GithubRepoEntity {
    return GithubRepoEntity(
        id = id,
        name = name,
        fullName = fullName,
        ownerName = owner.username,
        ownerAvatarUrl = owner.avatarUrl,
        description = description,
        stars = stargazersCount,
        forks = forksCount,
        language = language,
        visibility = visibility,
        url = htmlUrl
    )
}

fun GithubRepoEntity.toModel(): GithubRepoModel {
    return GithubRepoModel(
        id = id,
        name = name,
        fullName = fullName,
        owner = GithubOwnerModel(ownerName, ownerAvatarUrl),
        description = description,
        stars = stars,
        forks = forks,
        language = language,
        visibility = visibility,
        url = url
    )
}

