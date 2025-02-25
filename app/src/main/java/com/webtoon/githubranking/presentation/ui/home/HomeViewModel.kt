package com.webtoon.githubranking.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.webtoon.githubranking.domain.model.GithubRepoModel
import com.webtoon.githubranking.domain.usecase.GetGithubRepoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (
    private val getGithubRepoUseCase: GetGithubRepoUseCase
):ViewModel() {

    // 깃허브 랭킹 리스트
    private val _githubRepo = MutableStateFlow<PagingData<GithubRepoModel>>(PagingData.empty())
    val githubRepo: StateFlow<PagingData<GithubRepoModel>> = _githubRepo.asStateFlow()

    fun getGithubRepoList(
        query: String,
        sort: String
    ) {
        viewModelScope.launch {
            getGithubRepoUseCase(query, sort)
                .cachedIn(viewModelScope)
                .onEach { pagingData ->
                    Timber.d("새로운 데이터 로드됨: $pagingData")
                }
                .collectLatest { pagingData ->
                    _githubRepo.value = pagingData
                }
        }
    }
}