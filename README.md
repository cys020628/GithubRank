# 📖 Github Rank App

## 🖥️ 앱 미리보기

<table>
  <tr>
        <td><img src="https://github.com/user-attachments/assets/ec88d0cf-ed60-458a-b8ac-337e3c58a110" width="400"/></td>
  </tr>
  <tr>
    <td align="center">홈 화면</td>
  </tr>
</table>

## 📌 프로젝트 소개
GitHub의 인기 있는 리포지토리와 사용자 정보를 조회할 수 있는 Android 애플리케이션입니다. 최신 기술 스택을 활용하여 클린 아키텍처를 적용하였으며, Jetpack Compose 기반으로 개발되었습니다.

---

## 🛠 기술 스택
- **아키텍처**: Clean Architecture
- **UI**: Jetpack Compose, Navigation
- **이미지 로딩**: Coil
- **데이터**: Retrofit, Room, GitHub API
- **비동기 처리**: Coroutine, Flow
- **DI**: Hilt
- **로깅**: Timber
- **상태 관리**: Flow, ViewModel
- **로컬 저장소**: Room, DataStore Preference
- **페이징**: Paging 3
- **보안**: local에 API Key 숨기기

---

## 📱 주요 기능
- GitHub의 인기 있는 리포지토리 및 유저 목록 조회
- 검색 기능 제공 (리포지토리, 유저) -> 구현중
- 리포지토리 및 유저 상세 정보 제공 -> 구현중
- 즐겨찾기(북마크) 기능 -> 구현중
- 페이징을 활용한 효율적인 데이터 로딩
- 다크 모드 지원 -> 구현중

 ---

## 🏛 아키텍처
본 프로젝트는 **클린 아키텍처** 원칙을 따르며, 단일 모듈 구조(Single Module)로 개발되었습니다.  

---

## 🚀 코드 실행
✅ BuildConfig(Module) 

``` kotlin
val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
   buildConfigField(
            "String",
            "GITHUB_BASE_URL",
            "\"${properties.getProperty("GITHUB_BASE_URL")}\""
        )

        buildConfigField(
            "String",
            "GITHUB_TOKEN",
            "\"${properties.getProperty("GITHUB_TOKEN")}\""
        )
}

  buildFeatures {
        buildConfig = true
    }

```

✅ local.properties

``` kotlin
GITHUB_BASE_URL = https://api.github.com/
GITHUB_TOKEN = Bearer ${gitHub Token}
```
