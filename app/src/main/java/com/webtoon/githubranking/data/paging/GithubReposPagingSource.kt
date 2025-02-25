package com.webtoon.githubranking.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.webtoon.githubranking.BuildConfig
import com.webtoon.githubranking.data.local.db.GithubRepoDao
import com.webtoon.githubranking.data.local.preferences.PreferenceManager
import com.webtoon.githubranking.data.mapper.toEntity
import com.webtoon.githubranking.data.mapper.toModel
import com.webtoon.githubranking.data.remote.SearchGithubRepositoryApi
import com.webtoon.githubranking.domain.model.GithubRepoModel
import kotlinx.coroutines.flow.first

// GithubReposPagingSource는 PagingSource를 상속받아 페이징 데이터를 처리하는 클래스
class GithubReposPagingSource(
    private val api: SearchGithubRepositoryApi, // GitHub API를 호출하는 인터페이스
    private val query: String, // 검색할 키워드
    private val sort: String, // 정렬 기준 (예: stars, forks, updated 등)
    private val githubRepoDao: GithubRepoDao, // 로컬 데이터베이스 DAO
    private val preferenceManager: PreferenceManager // 사용자 선호도 설정을 관리하는 객체
) : PagingSource<Int, GithubRepoModel>() { // 첫 번째 제네릭(Int): 페이지 번호, 두 번째(GithubRepoModel): 데이터 모델

    // 데이터를 로드하는 함수 (페이징 라이브러리가 호출)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepoModel> {
        return try {
            val page = params.key ?: 1 // 현재 페이지 번호 (없으면 첫 페이지(1)로 설정)
            val currentTime = System.currentTimeMillis() // 현재 시간(밀리초 단위)
            val lastUpdated = preferenceManager.getLastUpdatedTime() // 마지막 업데이트 시간 가져오기
            val oneDayMillis = 24 * 60 * 60 * 1000 // 1일(24시간)의 밀리초 단위 변환

            // 로컬 캐시 데이터를 하루 이내에 업데이트한 적이 있다면 캐시 데이터 사용
            if (currentTime - lastUpdated < oneDayMillis) {
                val cachedData = githubRepoDao.getAllRepos().first() // 로컬 DB에서 모든 저장소 가져오기 (Flow의 첫 번째 데이터)
                val convertedData = cachedData.map { it.toModel() } // 엔티티 데이터를 모델 객체로 변환

                return LoadResult.Page(
                    data = convertedData, // 캐시 데이터 사용
                    prevKey = null, // 이전 페이지 없음
                    nextKey = null // 다음 페이지 없음 (로컬 캐시는 페이징 개념이 없기 때문)
                )
            }

            // GitHub API에서 저장소 목록 가져오기
            val response = api.getPopularRepositories(
                authorization = "${BuildConfig.GITHUB_TOKEN}", // 인증 토큰 추가
                query = query, // 검색어 전달
                sort = sort // 정렬 기준 전달
            )

            // API 응답이 비어있지 않으면 로컬 DB에 저장
            if (response.repositories.isNotEmpty()) {
                val repoEntities = response.repositories.map { it.toEntity() } // API 응답을 DB 엔티티로 변환
                githubRepoDao.clearRepos() // 기존 저장소 데이터 삭제
                githubRepoDao.insertRepos(repoEntities) // 새로운 데이터 삽입
                preferenceManager.setLastUpdatedTime(currentTime) // 마지막 업데이트 시간 저장
            }

            // API 응답 데이터를 Paging 라이브러리에 전달
            LoadResult.Page(
                data = response.repositories.map { it.toModel() }, // API에서 받은 데이터를 모델로 변환
                prevKey = if (page == 1) null else page - 1, // 첫 페이지라면 이전 페이지 없음, 그 외에는 이전 페이지 번호 제공
                nextKey = if (response.repositories.isEmpty()) null else page + 1 // 데이터가 없으면 다음 페이지 없음, 그렇지 않으면 다음 페이지 번호 제공
            )
        } catch (e: Exception) { // 예외 발생 시
            LoadResult.Error(e) // 오류 결과 반환
        }
    }

    // 페이징 데이터를 새로고침할 때 사용할 키 반환
    override fun getRefreshKey(state: PagingState<Int, GithubRepoModel>): Int? {
        return state.anchorPosition?.let { anchorPosition -> // 현재 위치의 인덱스(스크롤 위치)
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1) // 이전 페이지 키가 있으면 현재 페이지로 변환
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1) // 다음 페이지 키가 있으면 현재 페이지로 변환
        }
    }
}
