package com.webtoon.githubranking.data.local.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GithubRepoDao {
    @Query("SELECT * FROM github_repos ORDER BY stars DESC")
    fun getPagingSource(): PagingSource<Int, GithubRepoEntity>

    @Query("SELECT * FROM github_repos ORDER BY stars DESC")
    fun getAllReposFlow(): Flow<List<GithubRepoEntity>> // ✅ 최신 데이터 Flow로 제공

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepos(repos: List<GithubRepoEntity>)

    @Query("DELETE FROM github_repos")
    suspend fun clearRepos()
}
