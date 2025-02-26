package com.webtoon.githubranking.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webtoon.githubranking.data.local.preferences.PreferenceManager
import com.webtoon.githubranking.domain.usecase.GetGithubRepoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getGithubRepoUseCase: GetGithubRepoUseCase,
    private val sharedPreferenceManager: PreferenceManager
) : ViewModel() {

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    fun fetchAndSaveRepos(query: String, sort: String) {
        val oneDayMillis = 24 * 60 * 60 * 1000 // 24시간(1일) 밀리초 값
        val lastUpdated = sharedPreferenceManager.getLastUpdatedTime()
        val currentTime = System.currentTimeMillis()

        // **첫 실행이거나, 24시간이 지났다면 API 요청 실행**
        if (sharedPreferenceManager.isFirstLaunch() || (currentTime - lastUpdated) > oneDayMillis) {
            Timber.e(if (sharedPreferenceManager.isFirstLaunch()) "첫 실행" else "하루 이상 지남")

            viewModelScope.launch {
                getGithubRepoUseCase(query, sort)
                    .catch { exception -> _errorMessage.value = exception.message }
                    .collect { result ->
                        if (sharedPreferenceManager.isFirstLaunch()) {
                            sharedPreferenceManager.setFirstLaunchDone() //  첫 실행 여부 업데이트 (첫 실행일 경우에만)
                        }
                        sharedPreferenceManager.setLastUpdatedTime(System.currentTimeMillis()) //  마지막 업데이트 시간 저장
                        _loading.value = false
                        result.onFailure { error ->
                            _errorMessage.value = error.message
                        }
                    }
            }
        } else {
            _loading.value = false
            Timber.e("하루 이상 안 지남")
        }
    }
}