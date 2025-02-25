package com.webtoon.githubranking.presentation.theme


import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun GithubRankingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(), // 다크모드를 시스템 설정에 따라 자동 적용
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true, // Android 12 이상에서 동적 컬러 적용 여부
    content: @Composable () -> Unit // 테모 적용 후 감싸줄 UI 컨텐츠
) {
    val colorScheme = when { // 앱의 색상 테마 결정하는 부분
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // 한글 UI 가 있는 경우 비율 깨짐 방지를 위해 필수
    // 다양한 기기에서 일관된 글꼴 크기 유지 가능 및 가독성을 일정하게 유지 가능
    CompositionLocalProvider(
        LocalDensity provides Density(
            density = LocalDensity.current.density,
            fontScale = 1f // 폰트 크기 비율을 1로 설정하여 기본 폰트 크기 유지
        )
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}