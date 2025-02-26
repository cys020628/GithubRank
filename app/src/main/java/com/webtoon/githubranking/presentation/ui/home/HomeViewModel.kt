package com.webtoon.githubranking.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.webtoon.githubranking.domain.model.GithubRepoModel
import com.webtoon.githubranking.domain.usecase.GetAllGithubReposUseCase
import com.webtoon.githubranking.domain.usecase.GetPagedGithubReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getPagedGithubReposUseCase: GetPagedGithubReposUseCase,
    getAllGithubReposUseCase: GetAllGithubReposUseCase
) : ViewModel() {

    val repoPagingFlow: Flow<PagingData<GithubRepoModel>> =
        getPagedGithubReposUseCase().cachedIn(viewModelScope) // ✅ 페이징 데이터 캐싱

    val allReposFlow = getAllGithubReposUseCase()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList()) // ✅ 최신 데이터 Flow 캐싱
}