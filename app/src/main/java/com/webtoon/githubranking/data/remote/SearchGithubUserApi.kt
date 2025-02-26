package com.webtoon.githubranking.data.remote

import com.webtoon.githubranking.data.dto.GithubSearchUserItems
import retrofit2.http.GET
import retrofit2.http.Path

interface SearchGithubUserApi {
    @GET("users/{userName}/repos")
    suspend fun getUserRepositories(
        @Path("userName") userName:String
    ):List<GithubSearchUserItems>
}