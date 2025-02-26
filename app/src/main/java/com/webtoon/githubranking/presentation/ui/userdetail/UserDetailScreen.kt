package com.webtoon.githubranking.presentation.ui.userdetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.webtoon.githubranking.presentation.common.ComponentToolbar
import timber.log.Timber

@Composable
fun UserDetailScreen(
    userName: String,
    navHostController: NavHostController,
    userDetailViewModel: UserDetailViewModel = hiltViewModel()
) {

    val userInfoData = userDetailViewModel.userData.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        userDetailViewModel.searchUserData(userName)
    }
    Timber.e("이름 $userName")

    Scaffold(
        modifier = Modifier.background(Color.LightGray),
        topBar = {
            ComponentToolbar(
                title = userName,
                navigationIcon = {
                    IconButton(onClick = {
                        navHostController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        }

    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = userInfoData.value.firstOrNull()?.owner?.avatarUrl ?: ""
                    ),
                    contentDescription = "Owner Avatar",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "⭐ : ${userInfoData.value.firstOrNull()?.stargazersCount}",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(end = 8.dp)
                    )
                    Text(
                        text = "🍴 : ${userInfoData.value.firstOrNull()?.forksCount}",
                        fontSize = 20.sp,
                    )
                }
            }
        }
    }
}