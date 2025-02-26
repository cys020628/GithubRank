package com.webtoon.githubranking.data.local.preferences

import android.content.SharedPreferences
import javax.inject.Inject

class PreferenceManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    fun getLastUpdatedTime(): Long {
        return sharedPreferences.getLong("last_updated", 0L)
    }

    fun setLastUpdatedTime(time: Long) {
        sharedPreferences.edit().putLong("last_updated", time).apply()
    }

    fun isFirstLaunch(): Boolean {
        return sharedPreferences.getBoolean("is_first_launch", true)
    }

    fun setFirstLaunchDone() {
        sharedPreferences.edit().putBoolean("is_first_launch", false).apply()
    }
}
