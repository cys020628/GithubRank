package com.webtoon.githubranking.domain.model

data class GithubRepoModel(
    val id: Long, // 리포지토리 ID
    val name: String, // 리포지토리 이름
    val fullName: String, // 전체 이름 (예: "freeCodeCamp/freeCodeCamp")
    val owner: GithubOwnerModel, // 리포지토리 소유자
    val description: String?, // 설명
    val stars: Int, // ⭐️ Star 개수
    val forks: Int, // 🍴 Fork 개수
    val language: String?, // 주요 프로그래밍 언어
    val visibility: String, // 리포지토리 공개 범위
    val url: String // GitHub 리포지토리 URL
)

data class GithubOwnerModel(
    val username: String, // 소유자 GitHub 아이디
    val avatarUrl: String // 소유자 프로필 이미지 URL
)
