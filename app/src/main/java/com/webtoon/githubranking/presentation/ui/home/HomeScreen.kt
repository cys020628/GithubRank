package com.webtoon.githubranking.presentation.ui.home

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.webtoon.githubranking.domain.model.GithubRepoModel

@Composable
fun HomeScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {

    val githubRepoList = homeViewModel.githubRepo.collectAsLazyPagingItems()

    LaunchedEffect(Unit) {
        // 하트가 제일 많은 순으로 업데이트
        homeViewModel.getGithubRepoList(
            "stars:>0",
            "stars"
        )
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
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(githubRepoList.itemCount) { index ->
                    val repoList = githubRepoList[index]
                    if (repoList != null) {
                        GithubRepoItem(index + 1,repoList) //  개별 항목 UI
                    }
                }

            }
        }
    }
}

@Composable
fun GithubRepoItem(rank: Int, repo: GithubRepoModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { /* 클릭 시 상세 페이지 이동 가능 */ },
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

            AsyncImage(
                model = repo.owner.avatarUrl,
                contentDescription = "Owner Avatar",
                modifier = Modifier
                    .size(48.dp)
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
                    text = "⭐ ${repo.stars}  🍴 ${repo.forks}",
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