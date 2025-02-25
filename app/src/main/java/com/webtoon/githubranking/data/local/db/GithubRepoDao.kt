package com.webtoon.githubranking.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubRepoDao {
    @Query("SELECT * FROM github_repos")
    fun getAllRepos(): Flow<List<GithubRepoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<GithubRepoEntity>)

    @Query("DELETE FROM github_repos")
    suspend fun clearRepos()
}