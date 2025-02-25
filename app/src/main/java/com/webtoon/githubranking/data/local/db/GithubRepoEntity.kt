package com.webtoon.githubranking.data.local.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "github_repos")
data class GithubRepoEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val fullName: String,
    val ownerName: String,
    val ownerAvatarUrl: String,
    val description: String?,
    val stars: Int,
    val forks: Int,
    val language: String?,
    val visibility: String,
    val url: String
)