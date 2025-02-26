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

## 📂 프로젝트 구조

```markdown
githubranking/
│── data/                         # 데이터 관련 로직 (Data Layer)
│   ├── dto/                      # 데이터 전송 객체 (Data Transfer Object)
│   ├── local/                    # 로컬 데이터 저장소 (Room, SharedPreferences 등)
│   ├── mapper/                   # DTO ↔ 도메인 모델 매핑
│   ├── paging/                    # 페이징 처리 관련 로직
│   ├── remote/                    # 네트워크 API 관련 로직
│   ├── repository/                # 데이터 저장소 구현 (Repository Pattern)
│
│── di/                            # 의존성 주입 (Dependency Injection - Hilt)
│   ├── NetworkModule.kt          # 네트워크 관련 DI 모듈
│   ├── PreferenceModule.kt       # SharedPreferences 관련 DI 모듈
│   ├── RepositoryModule.kt       # Repository 관련 DI 모듈
│   ├── RoomModule.kt             # Room Database 관련 DI 모듈
│
│── domain/                        # 도메인 레이어 (비즈니스 로직)
│   ├── model/                    # 앱의 핵심 도메인 모델
│   ├── repository/                # Repository 인터페이스 정의
│   ├── usecase/                   # UseCase (비즈니스 로직 처리)
│
│── presentation/                   # UI 레이어 (Presentation Layer)
│   ├── common/                    # 공통 UI 컴포넌트
│   ├── theme/                     # 테마 및 스타일 관련 파일
│   ├── ui/                        # 개별 화면 (Compose UI)
│   ├── util/                      # UI 관련 유틸리티 함수
│
│── App.kt                         # 애플리케이션 클래스 (Hilt 적용)

```

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

## 🛠 **Trouble Shooting & 해결 과정**
### 🚨 **GitHub API 과요청(Rate Limit 초과) 문제**
GitHub API는 비인증 요청 시 **1시간에 60회**, 인증된 요청 시 **5,000회**의 요청 제한이 존재합니다.  
하지만, 많은 사용자 또는 빈번한 API 호출로 인해 과요청이 발생하면 **API가 차단되는 문제**가 있었습니다.

### 🏗 **해결 과정**
1. **API 요청 횟수를 줄이기 위해 Room을 활용한 로컬 캐싱 적용**
2. **앱 최초 실행 시 API 데이터를 가져와 Room에 저장**
3. **이후에는 Room에서 데이터를 가져오고, 24시간마다 갱신하도록 설정**
4. **SplashScreen에서 데이터 동기화 후 `HomeScreen`으로 이동**
5. **GitHub API 요청 없이 Room에서 페이징 데이터를 제공**하여 불필요한 API 요청 방지

---

## ✅ **Trouble Shooting: API 요청 최적화 (Room 캐싱 + 24시간 갱신)**

### **1️⃣ 문제 발생**
> GitHub API 요청이 많아지면서 Rate Limit(과요청) 문제 발생  
> → API 요청 횟수를 줄이고, 효율적으로 데이터를 캐싱해야 함.

### **2️⃣ 문제 해결**
| 해결 방법 | 적용 방식 |
|------|------|
| **초기 실행 시만 API에서 데이터 가져오기** | 앱 최초 실행 시 GitHub API에서 데이터를 받아 Room에 저장 |
| **24시간마다 데이터 갱신** | `System.currentTimeMillis()`를 이용하여 24시간이 지나면 API 호출 후 Room 업데이트 |
| **SplashScreen에서 데이터 동기화** | `fetchAndSaveRepos()`를 실행하여 Room을 최신 상태로 유지 후 `HomeScreen`으로 이동 |
| **페이징 적용** | `Room PagingSource`를 활용하여 UI에서 데이터 페이징 처리 |

---
