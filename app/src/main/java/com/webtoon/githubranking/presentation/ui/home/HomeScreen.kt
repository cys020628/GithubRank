package com.webtoon.githubranking.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.webtoon.githubranking.domain.model.GithubRepoModel
import com.webtoon.githubranking.presentation.util.formatWithComma

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    // 페이징 데이터 처리용
    val repoList = homeViewModel.repoPagingFlow.collectAsLazyPagingItems()
    // 모든 데이터 처리용
    val allRepos by homeViewModel.allReposFlow.collectAsStateWithLifecycle()

    /**
     * @param LaunchedEffect Jetpack Compose에서 특정 키 값이 변경될 때 코드를 실행하는 Composable 함수
     * @param Unit composable이 처음 composition(구성) 될떄 한번만 실행 이는 Unit 값이 절대 변하지 않기 때문 같은 이유로 Unit이 아닌 true도 가능
     * ex)
     * LaunchedEffect(userId)	userId가 변경될 때마다 다시 실행
     * LaunchedEffect(list.size)	list.size 값이 바뀔 때마다 실행
     * LaunchedEffect(state.value)	state.value가 변경될 때마다 실행
     * LaunchedEffect(navBackStackEntry)	네비게이션 상태가 변경될 때마다 실행
     */
    LaunchedEffect(Unit) {

    }

    Scaffold(
        modifier = Modifier.background(Color.White)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Text(
                text = "GitHub Top 100",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )

            //  리스트로 데이터 표시
            LazyColumn(
                modifier = Modifier.fillMaxSize(), // 전체 화면을 차지하도록 설정
                contentPadding = PaddingValues(16.dp) // 리스트의 전체 padding 값을 16.dp 로 설정
            ) {
                items(repoList.itemCount) { index ->
                    val repoList = repoList[index] // 현재 리스트의 N번째 데이터
                    if (repoList != null) {
                        GithubRepoItem(
                            index + 1,
                            repoList,
                            navHostController
                        )  // repoList가 null 아닐때만 GithubRepoItem을 렌더링
                    }
                }
            }
        }
    }
}

@Composable
fun GithubRepoItem(
    rank: Int,
    repo: GithubRepoModel,
    navHostController: NavHostController,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navHostController.navigate("userDetail/${repo.name}")
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${rank}위",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .align(Alignment.CenterVertically)
            )

            Image(
                painter = rememberAsyncImagePainter(model = repo.owner.avatarUrl),
                contentDescription = "Owner Avatar",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                // 리포지토리 이름
                Text(
                    text = repo.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                // 스타 개수
                Text(
                    text = "⭐ ${repo.stars.formatWithComma()}  🍴 ${repo.forks.formatWithComma()}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                // 프로그래밍 언어 (nullable 처리)
                repo.language?.let {
                    Text(
                        text = "🖥 Language: $it",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}