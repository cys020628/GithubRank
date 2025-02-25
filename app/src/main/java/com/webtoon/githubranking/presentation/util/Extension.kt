package com.webtoon.githubranking.presentation.util

fun Int.formatWithComma(): String {
    return "%,d".format(this) // 천 단위마다 콤마 추가
}

fun Long.formatWithComma(): String {
    return "%,d".format(this) // Long 타입도 지원
}

fun Double.formatWithComma(decimalPlaces: Int = 2): String {
    return "%,.${decimalPlaces}f".format(this) // 소수점 이하 자리수 지정 가능
}
