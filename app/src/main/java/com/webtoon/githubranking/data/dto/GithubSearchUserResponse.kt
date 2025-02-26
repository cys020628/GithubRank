package com.webtoon.githubranking.data.dto

import com.google.gson.annotations.SerializedName

// 🔹 개별 리포지토리를 나타내는 DTO
data class GithubSearchUserItems(
    @SerializedName("id") val id: Long, // 리포지토리 고유 ID
    @SerializedName("name") val name: String, // 리포지토리 이름
    @SerializedName("full_name") val fullName: String, // 전체 리포지토리 경로 (예: "cys020628/WebtoonInfoBasic")
    @SerializedName("private") val isPrivate: Boolean, // 리포지토리의 공개 여부 (true: 비공개, false: 공개)

    @SerializedName("owner") val owner: GithubSearchUserOwner, // 리포지토리 소유자 정보
    @SerializedName("html_url") val htmlUrl: String, // GitHub에서 해당 리포지토리의 웹 주소
    @SerializedName("description") val description: String?, // 리포지토리 설명 (null 가능)
    @SerializedName("fork") val isFork: Boolean, // Fork 여부 (true: 다른 리포지토리를 Fork한 것)

    @SerializedName("created_at") val createdAt: String, // 리포지토리 생성 날짜 (ISO 8601 형식)
    @SerializedName("updated_at") val updatedAt: String, // 마지막 업데이트 날짜 (ISO 8601 형식)
    @SerializedName("pushed_at") val pushedAt: String, // 마지막 코드 푸시 날짜 (ISO 8601 형식)

    @SerializedName("stargazers_count") val stargazersCount: Int, // ⭐️ Star 개수
    @SerializedName("watchers_count") val watchersCount: Int, // 👀 Watchers 개수
    @SerializedName("forks_count") val forksCount: Int, // 🍴 Fork 개수
    @SerializedName("open_issues_count") val openIssuesCount: Int, // 📝 오픈된 이슈 개수

    @SerializedName("language") val language: String?, // 주요 프로그래밍 언어 (null 가능)
    @SerializedName("topics") val topics: List<String>?, // 📌 관련 태그(토픽) 리스트 (예: "react", "kotlin")

    @SerializedName("default_branch") val defaultBranch: String, // 기본 브랜치 이름 (예: "main", "master")
    @SerializedName("size") val size: Int, // 📂 저장소 크기 (단위: KB)
    @SerializedName("license") val license: GithubSearchUserLicense?, // 📜 라이선스 정보 (nullable)

    @SerializedName("has_issues") val hasIssues: Boolean, // 🛠️ 이슈(issues) 기능 활성화 여부
    @SerializedName("has_projects") val hasProjects: Boolean, // 📌 프로젝트 기능 활성화 여부
    @SerializedName("has_downloads") val hasDownloads: Boolean, // ⬇️ 다운로드 기능 활성화 여부
    @SerializedName("has_wiki") val hasWiki: Boolean // 📖 위키 기능 활성화 여부
)

// 🔹 리포지토리 소유자 정보 DTO
data class GithubSearchUserOwner(
    @SerializedName("login") val username: String, // 사용자 GitHub 아이디
    @SerializedName("id") val id: Long, // 사용자 고유 ID
    @SerializedName("avatar_url") val avatarUrl: String, // 프로필 이미지 URL
    @SerializedName("html_url") val profileUrl: String // GitHub 프로필 페이지 URL
)

// 🔹 저장소의 라이선스 정보 DTO
data class GithubSearchUserLicense(
    @SerializedName("key") val key: String, // 라이선스 키 (예: "mit", "apache-2.0")
    @SerializedName("name") val name: String, // 라이선스 이름 (예: "MIT License")
    @SerializedName("spdx_id") val spdxId: String?, // SPDX 식별자 (예: "MIT")
    @SerializedName("url") val url: String? // 라이선스 세부 정보 URL (nullable)
)

