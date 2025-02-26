package com.webtoon.githubranking.di

import com.webtoon.githubranking.data.repository.GithubRepoListRepositoryImpl
import com.webtoon.githubranking.data.repository.GithubUserInfoRepositoryImpl
import com.webtoon.githubranking.domain.repository.GithubRepoListRepository
import com.webtoon.githubranking.domain.repository.GithubUserInfoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideGithubRepoListRepository(githubRepoListRepositoryImpl: GithubRepoListRepositoryImpl): GithubRepoListRepository

    @Singleton
    @Binds
    abstract fun provideGithubUserInfoRepository(githubUserInfoRepositoryImpl: GithubUserInfoRepositoryImpl): GithubUserInfoRepository
}