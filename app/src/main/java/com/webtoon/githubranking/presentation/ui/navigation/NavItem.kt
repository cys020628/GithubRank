package com.webtoon.githubranking.presentation.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

sealed class BottomNavItem(
    val route: String,
    val icon: @Composable () -> Unit,
    val label: String
) {
    data object Home : BottomNavItem(
        route = "home",
        icon = { Icon(Icons.Filled.Home, contentDescription = null) },
        label = "홈"
    )
    data object Search : BottomNavItem(
        route = "search",
        icon = { Icon(Icons.Filled.Search, contentDescription = null) },
        label = "검색"
    )
    data object StarMark : BottomNavItem(
        route = "bookmark",
        icon = { Icon(Icons.Filled.Star, contentDescription = null) },
        label = "즐겨찾기"
    )
    data object MyPage : BottomNavItem(
        route = "myPage",
        icon = { Icon(Icons.Filled.Person, contentDescription = null) },
        label = "마이"
    )
}

sealed class MainNavItem(val route: String, val label: String) {
    // 스플래쉬 화면
    data object Splash : MainNavItem("splash", "스플래쉬")
    // 웹뷰 화면
    data object WebView:MainNavItem("webview","웹뷰")
}
