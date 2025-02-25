package com.webtoon.githubranking

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App:Application() {

    override fun onCreate() {
        super.onCreate()
        // Timber Set
        Timber.plant(Timber.DebugTree())
    }
}