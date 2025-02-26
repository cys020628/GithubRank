package com.webtoon.githubranking.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.webtoon.githubranking.data.dto.GithubSearchUserItems
import com.webtoon.githubranking.data.dto.GithubSearchUserLicense
import com.webtoon.githubranking.data.dto.GithubSearchUserOwner
import com.webtoon.githubranking.domain.model.GithubOwnerModel
import com.webtoon.githubranking.domain.model.LicenseModel
import com.webtoon.githubranking.domain.model.OwnerModel

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// 🔹 ISO 8601 형식의 날짜 문자열을 LocalDateTime으로 변환하는 확장 함수
@RequiresApi(Build.VERSION_CODES.O)
private fun String.toLocalDateTime(): LocalDateTime {
    return LocalDateTime.parse(this, DateTimeFormatter.ISO_DATE_TIME)
}

// 🔹 GithubSearchUserItems → RepositoryModel 변환 함수
@RequiresApi(Build.VERSION_CODES.O)
fun GithubSearchUserItems.toModel(): GithubOwnerModel {
    return GithubOwnerModel(
        id = id,
        name = name,
        fullName = fullName,
        isPrivate = isPrivate,
        owner = owner.toModel(), // 소유자 정보 변환
        htmlUrl = htmlUrl,
        description = description ?: "", // null 방지
        isFork = isFork,

        createdAt = createdAt.toLocalDateTime(), // 날짜 변환
        updatedAt = updatedAt.toLocalDateTime(),
        pushedAt = pushedAt.toLocalDateTime(),

        stargazersCount = stargazersCount,
        watchersCount = watchersCount,
        forksCount = forksCount,
        openIssuesCount = openIssuesCount,

        language = language ?: "Unknown", // null 방지
        topics = topics ?: emptyList(), // null 방지

        defaultBranch = defaultBranch,
        size = size,
        license = license?.toModel(), // 라이선스 변환

        hasIssues = hasIssues,
        hasProjects = hasProjects,
        hasDownloads = hasDownloads,
        hasWiki = hasWiki
    )
}

// 🔹 GithubSearchUserOwner → OwnerModel 변환 함수
fun GithubSearchUserOwner.toModel(): OwnerModel {
    return OwnerModel(
        username = username,
        id = id,
        avatarUrl = avatarUrl,
        profileUrl = profileUrl
    )
}

// 🔹 GithubSearchUserLicense → LicenseModel 변환 함수
fun GithubSearchUserLicense.toModel(): LicenseModel {
    return LicenseModel(
        key = key,
        name = name,
        spdxId = spdxId,
        url = url
    )
}
