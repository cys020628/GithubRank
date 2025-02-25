package com.webtoon.githubranking.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.webtoon.githubranking.presentation.ui.home.HomeScreen
import com.webtoon.githubranking.presentation.ui.splash.SplashScreen

@Composable
fun NavGraph(
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = MainNavItem.Splash.route,
        modifier = modifier
    ) {
        // Splash
        composable(MainNavItem.Splash.route) { SplashScreen(navHostController) }

        // Home
        composable(BottomNavItem.Home.route) { HomeScreen(navHostController) }
    }
}