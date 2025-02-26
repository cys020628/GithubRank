package com.webtoon.githubranking.presentation.ui.userdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webtoon.githubranking.domain.model.GithubOwnerModel
import com.webtoon.githubranking.domain.usecase.GetUserInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserInfoUseCase: GetUserInfoUseCase
) : ViewModel() {

    private val _userData = MutableStateFlow<List<GithubOwnerModel>>(emptyList())
    val userData: StateFlow<List<GithubOwnerModel>> = _userData.asStateFlow()

    fun searchUserData(userName: String) {
        viewModelScope.launch {
            getUserInfoUseCase(userName)
                .collectLatest { result ->
                    result.fold(
                        onSuccess = { userInfo ->
                            _userData.value = userInfo
                            Timber.e("성공 ${_userData.value}")
                        },
                        onFailure = { error ->
                            Timber.e("에러 ${error.message}")
                        }
                    )
                }
        }
    }
}