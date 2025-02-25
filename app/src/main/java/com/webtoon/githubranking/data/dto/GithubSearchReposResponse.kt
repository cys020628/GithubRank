package com.webtoon.githubranking.data.dto

import com.google.gson.annotations.SerializedName


data class GithubSearchReposResponse(
    @SerializedName("total_count") val totalCount: Int, // 검색된 전체 리포지토리 개수
    @SerializedName("incomplete_results") val incompleteResults: Boolean, // 검색 결과가 완전한지 여부 (true이면 일부 결과가 생략됨)
    @SerializedName("items") val repositories: List<GithubSearchReposItems> // 검색된 리포지토리 리스트
)

// 개별 리포지토리를 나타내는 DTO
data class GithubSearchReposItems(
    @SerializedName("id") val id: Long, // 리포지토리의 고유 ID
    @SerializedName("name") val name: String, // 리포지토리 이름
    @SerializedName("full_name") val fullName: String, // 소유자 포함 전체 리포지토리 이름 (예: freeCodeCamp/freeCodeCamp)
    @SerializedName("private") val isPrivate: Boolean, // 리포지토리의 공개 여부 (true: 비공개, false: 공개)
    @SerializedName("owner") val owner: GithubSearchReposOwner, // 리포지토리 소유자 정보
    @SerializedName("html_url") val htmlUrl: String, // GitHub에서 해당 리포지토리의 웹 주소
    @SerializedName("description") val description: String?, // 리포지토리 설명 (null 가능)
    @SerializedName("fork") val isFork: Boolean, // 이 리포지토리가 다른 리포지토리를 Fork한 것인지 여부
    @SerializedName("created_at") val createdAt: String, // 리포지토리 생성 날짜 (ISO 8601 형식)
    @SerializedName("updated_at") val updatedAt: String, // 마지막 업데이트 날짜 (ISO 8601 형식)
    @SerializedName("pushed_at") val pushedAt: String, // 마지막 코드 푸시 날짜 (ISO 8601 형식)
    @SerializedName("stargazers_count") val stargazersCount: Int, // Star(⭐️) 개수
    @SerializedName("watchers_count") val watchersCount: Int, // Watchers(👀) 개수 (보통 stargazers_count와 동일)
    @SerializedName("forks_count") val forksCount: Int, // Fork(🍴) 개수
    @SerializedName("open_issues_count") val openIssuesCount: Int, // 오픈된 이슈 개수
    @SerializedName("language") val language: String?, // 주요 프로그래밍 언어 (null 가능)
    @SerializedName("license") val license: GithubLicense?, // 라이선스 정보 (null 가능)
    @SerializedName("topics") val topics: List<String>?, // 관련 태그(토픽) 리스트 (예: "react", "javascript", "opensource")
    @SerializedName("visibility") val visibility: String, // 리포지토리 공개 범위 (예: "public")
    @SerializedName("default_branch") val defaultBranch: String, // 기본 브랜치 이름 (예: "main", "master")
    @SerializedName("score") val score: Float // 검색 결과의 점수 (GitHub 내부 알고리즘 사용)
)

// 리포지토리 소유자 정보 DTO
data class GithubSearchReposOwner(
    @SerializedName("login") val username: String, // 소유자 GitHub 사용자명
    @SerializedName("id") val id: Long, // 소유자 고유 ID
    @SerializedName("avatar_url") val avatarUrl: String, // 프로필 이미지 URL
    @SerializedName("html_url") val profileUrl: String // GitHub 프로필 페이지 URL
)

// 리포지토리의 라이선스 정보 DTO
data class GithubLicense(
    @SerializedName("key") val key: String, // 라이선스 키 (예: "bsd-3-clause")
    @SerializedName("name") val name: String, // 라이선스 이름 (예: "BSD 3-Clause License")
    @SerializedName("spdx_id") val spdxId: String?, // SPDX Identifier (예: "BSD-3-Clause", null 가능)
    @SerializedName("url") val url: String? // 라이선스 상세 정보 페이지 URL (null 가능)
)