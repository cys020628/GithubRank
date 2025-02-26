package com.webtoon.githubranking.domain.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

// 🔹 GitHub 리포지토리 모델
data class GithubOwnerModel(
    val id: Long, // 리포지토리 고유 ID
    val name: String, // 리포지토리 이름
    val fullName: String, // 전체 리포지토리 경로 (예: "cys020628/WebtoonInfoBasic")
    val isPrivate: Boolean, // 리포지토리의 공개 여부 (true: 비공개, false: 공개)

    val owner: OwnerModel, // 리포지토리 소유자 정보
    val htmlUrl: String, // GitHub에서 해당 리포지토리의 웹 주소
    val description: String, // 리포지토리 설명 (기본값: "")
    val isFork: Boolean, // Fork 여부 (true: 다른 리포지토리를 Fork한 것)

    val createdAt: LocalDateTime, // 리포지토리 생성 날짜
    val updatedAt: LocalDateTime, // 마지막 업데이트 날짜
    val pushedAt: LocalDateTime, // 마지막 코드 푸시 날짜

    val stargazersCount: Int, // ⭐️ Star 개수
    val watchersCount: Int, // 👀 Watchers 개수
    val forksCount: Int, // 🍴 Fork 개수
    val openIssuesCount: Int, // 📝 오픈된 이슈 개수

    val language: String, // 주요 프로그래밍 언어 (기본값: "Unknown")
    val topics: List<String>, // 📌 관련 태그(토픽) 리스트 (기본값: 빈 리스트)

    val defaultBranch: String, // 기본 브랜치 이름
    val size: Int, // 📂 저장소 크기 (단위: KB)
    val license: LicenseModel?, // 📜 라이선스 정보 (nullable)

    val hasIssues: Boolean, // 🛠️ 이슈(issues) 기능 활성화 여부
    val hasProjects: Boolean, // 📌 프로젝트 기능 활성화 여부
    val hasDownloads: Boolean, // ⬇️ 다운로드 기능 활성화 여부
    val hasWiki: Boolean // 📖 위키 기능 활성화 여부
)

// 🔹 저장소 소유자 모델
data class OwnerModel(
    val username: String, // 사용자 GitHub 아이디
    val id: Long, // 사용자 고유 ID
    val avatarUrl: String, // 프로필 이미지 URL
    val profileUrl: String // GitHub 프로필 페이지 URL
)

// 🔹 라이선스 모델
data class LicenseModel(
    val key: String, // 라이선스 키 (예: "mit", "apache-2.0")
    val name: String, // 라이선스 이름 (예: "MIT License")
    val spdxId: String?, // SPDX 식별자 (예: "MIT")
    val url: String? // 라이선스 세부 정보 URL (nullable)
)
