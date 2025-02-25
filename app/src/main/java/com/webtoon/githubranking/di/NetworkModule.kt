package com.webtoon.githubranking.di

import com.webtoon.githubranking.BuildConfig
import com.webtoon.githubranking.data.remote.SearchGithubRepositoryApi
import com.webtoon.githubranking.data.remote.SearchGithubUserApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideGithubRetrofit(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit
            .Builder()
            .baseUrl(BuildConfig.GITHUB_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideSearchGithubReposApi(retrofit: Retrofit): SearchGithubRepositoryApi {
        return retrofit.create(SearchGithubRepositoryApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSearchGithubUserApi(retrofit: Retrofit): SearchGithubUserApi {
        return retrofit.create(SearchGithubUserApi::class.java)
    }
}