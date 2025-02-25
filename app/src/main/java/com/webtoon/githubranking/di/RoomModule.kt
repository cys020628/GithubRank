package com.webtoon.githubranking.di

import android.content.Context
import androidx.room.Room
import com.webtoon.githubranking.data.local.db.GithubDatabase
import com.webtoon.githubranking.data.local.db.GithubRepoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GithubDatabase {
        return Room.databaseBuilder(
            context,
            GithubDatabase::class.java,
            "github_database"
        ).build()
    }

    @Provides
    fun provideGithubRepoDao(database: GithubDatabase): GithubRepoDao {
        return database.githubRepoDao()
    }
}