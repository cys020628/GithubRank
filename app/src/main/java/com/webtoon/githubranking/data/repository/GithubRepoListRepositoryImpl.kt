package com.webtoon.githubranking.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.webtoon.githubranking.data.local.db.GithubRepoDao
import com.webtoon.githubranking.data.local.preferences.PreferenceManager
import com.webtoon.githubranking.data.paging.GithubReposPagingSource
import com.webtoon.githubranking.data.remote.SearchGithubRepositoryApi
import com.webtoon.githubranking.domain.model.GithubRepoModel
import com.webtoon.githubranking.domain.repository.GithubRepoListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GithubRepoListRepositoryImpl @Inject constructor(
    private val api: SearchGithubRepositoryApi,
    private val githubRepoDao: GithubRepoDao, // ✅ Room DAO 추가
    private val preferenceManager: PreferenceManager // ✅ SharedPreferences 추가
) : GithubRepoListRepository {

    override fun getGithubRepo(query: String, sort: String): Flow<PagingData<GithubRepoModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // 한 페이지에 로드할 아이템 개수 (20개)
                enablePlaceholders = false,  // true이면 자리 표시자(Placeholder) 사용 가능, false이면 사용 안 함
                maxSize = 100 // Paging 캐시에 저장할 최대 아이템 개수 (100개)
            ),
            pagingSourceFactory = {
                // 페이징 데이터를 가져오는 PagingSource를 생성하는 람다 함수
                GithubReposPagingSource(
                    api = api, // GitHub API 호출을 위한 Retrofit 서비스 인터페이스
                    query = query, // 검색 키워드 전달
                    sort = sort, // 정렬 기준 전달
                    githubRepoDao = githubRepoDao, // 로컬 데이터베이스 (Room) 접근 객체
                    preferenceManager = preferenceManager // 사용자 선호도 설정을 관리하는 객체
                )
            }
        ).flow
    }
}
