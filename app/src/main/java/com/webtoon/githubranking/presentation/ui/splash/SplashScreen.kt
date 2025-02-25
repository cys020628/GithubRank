package com.webtoon.githubranking.presentation.ui.splash

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.webtoon.githubranking.R
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController
) {

    val context = LocalContext.current

    // 애니메이션을 위한 상태 변수 설정
    var visible by remember { mutableStateOf(false) }

    // 로고의 크기 애니메이션 (작은 크기에서 커짐)
    val scale by animateFloatAsState(
        targetValue = if (visible) 1f else 0.5f,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
    )

    // 로고의 투명도 애니메이션 (서서히 나타남)
    val alpha by animateFloatAsState(
        targetValue = if (visible) 1f else 0f,
        animationSpec = tween(durationMillis = 2000, easing = FastOutSlowInEasing)
    )

    LaunchedEffect(Unit) {
        visible = true
        delay(2000)
        navController.navigate("home") {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "GitHubRank",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp) // 로고와 간격 조절
            )

            Image(
                painter = painterResource(id = R.drawable.ic_rank_logo),
                contentDescription = "앱 로고",
                modifier = Modifier
                    .size(200.dp * scale) // 크기 애니메이션 적용
                    .graphicsLayer(alpha = alpha) // 투명도 애니메이션 적용
            )
        }
    }
}