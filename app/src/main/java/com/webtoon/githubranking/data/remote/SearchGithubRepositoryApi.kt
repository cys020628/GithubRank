package com.webtoon.githubranking.data.remote

import com.webtoon.githubranking.data.dto.GithubSearchReposResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchGithubRepositoryApi {
    @GET("search/repositories")
    suspend fun getPopularRepositories(
        // 검색 조건
        @Query("q") query :String,
        // 정렬 방식
        @Query("sort") sort:String,
        // 올림차순 내림차순
        @Query("order") order:String = "desc"
    ):GithubSearchReposResponse
}