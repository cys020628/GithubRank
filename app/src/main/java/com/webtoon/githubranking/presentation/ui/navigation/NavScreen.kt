package com.webtoon.githubranking.presentation.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

@Composable
fun NavScreen() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier
            .statusBarsPadding()
            .safeDrawingPadding(),
        bottomBar = {
            if (navController.shouldShowBottomBar()) {
                Column {
                    HorizontalDivider(
                        thickness = 0.7.dp,
                        color = Color.LightGray,
                    )
                    //BottomNavigationBar(navController)
                }
            }
        }
    ) { paddingValues ->
        NavGraph(navController, Modifier.padding(paddingValues))
    }
}