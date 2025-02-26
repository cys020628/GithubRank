package com.webtoon.githubranking.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.webtoon.githubranking.BuildConfig
import com.webtoon.githubranking.data.mapper.toModel
import com.webtoon.githubranking.data.remote.SearchGithubRepositoryApi
import com.webtoon.githubranking.domain.model.GithubRepoModel

// GithubReposPagingSource는 PagingSource를 상속받아 페이징 데이터를 처리하는 클래스
class GithubReposPagingSource(
    private val api: SearchGithubRepositoryApi, // GitHub API를 호출하는 인터페이스
    private val query: String, // 검색할 키워드
    private val sort: String, // 정렬 기준 (예: stars, forks, updated 등)
) : PagingSource<Int, GithubRepoModel>() { // 첫 번째 제네릭(Int): 페이지 번호, 두 번째(GithubRepoModel): 데이터 모델

    // 데이터를 로드하는 함수 (페이징 라이브러리가 호출)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GithubRepoModel> {
        return try {
            val page = params.key ?: 1 // 현재 페이지 번호 (없으면 첫 페이지(1)로 설정)

            // GitHub API에서 저장소 목록 가져오기
            val response = api.getPopularRepositories(
                authorization = "${BuildConfig.GITHUB_TOKEN}", // 인증 토큰 추가
                query = query, // 검색어 전달
                sort = sort // 정렬 기준 전달
            )

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
