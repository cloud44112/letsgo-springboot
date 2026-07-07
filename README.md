# ✈️ LetsGo (Travel Schedule Planner)

> 한국관광공사 Tour API 연동 기반 개인 및 공유 여행 일정 플래너 웹 서비스

LetsGo는 사용자가 여행 목적지(숙소, 음식점, 레저 등)를 탐색하고, 자신만의 여행 일정을 상세히 계획(방문 순서, 예산, 할 일 등)할 수 있는 Spring Boot 기반 웹 애플리케이션입니다. 또한, 작성한 일정을 다른 사용자에게 공유하여 권한을 부여하거나 커뮤니티에 배포하여 추천 및 피드백을 주고받을 수 있습니다.

---

## 🌟 주요 기능 (Key Features)

### 👤 1. 회원 관리 및 보안 (User & Auth)
* **인증/보안**: Spring Security 기반 회원 로그인/로그아웃 처리.
* **사용자 편의 기능**: 아이디 찾기 및 비밀번호 재설정 기능 제공.

### 🗺️ 2. 관광 정보 탐색 (Tour API 연동)
* **공공데이터포털 연동**: 한국관광공사 국문 관광정보 서비스(Tour API)를 통해 실시간 숙소(Stay), 음식점(Restaurant), 레저스포츠(Leisure) 정보 수집.
* **지역 기반 검색**: 서울 및 주요 관광지 필터링.
* **추천 시스템**: 상세 정보 내 좋아요(Like) 누적 및 조회순 기반 관광지 랭킹 제공.

### 📅 3. 개인 여행 일정 작성 (My Schedule)
* **일정 설계**: 여행 일정 생성 시 방문할 장소들의 방문 순서(Visit Order) 및 장소 간 이동 거리 계산.
* **예산 및 체크리스트**: 일정 내 세부 예산(Budget) 및 체크리스트(Todo Checklist) 관리.
* **협업 관리**: 특정 일정을 타인과 공유하여 읽기/쓰기 권한을 조절하는 공유 협업 기능.

### 💬 4. 일정 공유 커뮤니티 (Post Schedule)
* **커뮤니티 공유**: 완성된 일정을 게시판에 공유하고, 익명 작성 여부 선택 가능.
* **소셜 인터랙션**: 추천(좋아요) 및 조회수 측정.
* **유해물 신고**: 부적절한 게시글에 대한 신고 접수 기능.

### 🛠️ 5. 관리자 대시보드 (Admin Dashboard)
* **통계 모니터링**: 사용자 현황, 활성 게시물, 신고 접수 건수 시각화.
* **데이터 관리**: 가입된 사용자 목록 제어, 전체 공유글 모니터링 및 숨김 처리.
* **신고 처리**: 접수된 신고에 따른 게시글 처리 상태 관리.
* **배치 수집**: Tour API를 호출하여 숙소, 레저, 음식점 공공 데이터를 시스템에 수동/배치 임포트.

---
## 🏗️ 아키텍처 (Architecture)
<img width="1069" height="678" alt="image" src="https://github.com/user-attachments/assets/ab533e12-43f7-4966-af2a-38cec6d47187" />

---

## 🛠️ 기술 스택 (Tech Stack)

### Backend
* **Language / Framework**: Java 17, Spring Boot 3.5.14
* **Database & ORM**: MariaDB, Spring Data JPA, MyBatis, PageHelper
* **Security**: Spring Security, Thymeleaf Security

### Frontend
* **Templating**: Thymeleaf
* **Base**: HTML5, CSS3, JavaScript

### Build
* Maven (mvnw)

---

## 📂 프로젝트 구조 (Directory Structure)

```text
src/main/java/com/travel/letsgospringboot/
├── admin          # 관리자 기능 (회원/게시글/신고 관리 및 대시보드)
├── advice         # 컨트롤러 전역 예외 핸들링 및 공통 어드바이스
├── common         # 공통 유틸리티 클래스 및 설정
├── exception      # 비즈니스 로직용 커스텀 예외 클래스
├── myschedule     # 개인 여행 일정 설계, 방문지 및 공유 설정 (JPA/MyBatis)
├── place          # 관광지(숙소/레저/식당) 상세 및 좋아요 정보 처리
├── postschedule   # 공유 커뮤니티 게시판 및 신고 관리
├── tourapi        # 한국관광공사 API 연동 클라이언트 및 데이터 임포터
└── user           # 사용자 계정 등록, 수정, 로그인 및 권한 관리
```

---

## 🚀 시작 가이드 (Getting Started)

### 📋 1. 사전 요구사항
* Java 17 JDK 설치
* MariaDB (또는 MySQL 호환 데이터베이스)
* 한국관광공사 Tour API 서비스 인증키 (공공데이터포털 신청 필요)

### 🗄️ 2. 데이터베이스 설정 (Database Setup)
1. MariaDB에 접속하여 데이터베이스를 생성합니다.
   ```sql
   CREATE DATABASE letsgo DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```
2. 프로젝트 루트의 `SQL/MariaDB_DDL.sql` 스크립트를 실행하여 테이블과 시퀀스를 생성합니다.
3. 초기 실행을 위해 더미 데이터가 필요하다면 `SQL/DummyData.sql`을 실행해 줍니다.

### ⚙️ 3. 환경 변수 설정 (Environment Variables)
애플리케이션 실행 전, 시스템 환경 변수(Environment Variables) 또는 IDE 실행 환경에 다음 설정을 입력해야 합니다.

| 환경 변수명 | 설명 | 예시 |
| :--- | :--- | :--- |
| `DB_URL` | MariaDB 연결 주소 | `jdbc:mariadb://localhost:3306/letsgo` |
| `DB_USERNAME` | 데이터베이스 계정명 | `root` |
| `DB_PASSWORD` | 데이터베이스 패스워드 | `your_password` |
| `SERVICE_KEY` | Tour API 서비스 키 | `인코딩 또는 디코딩된 공공데이터 API 키` |

### 🏃‍♂️ 4. 애플리케이션 실행
프로젝트 루트 경로에서 메이븐 래퍼(Maven Wrapper)를 사용하여 실행합니다.

#### Windows (PowerShell)
```powershell
.\mvnw.cmd spring-boot:run
```

#### Windows (CMD)
```cmd
mvnw.cmd spring-boot:run
```

#### Linux / macOS
```bash
./mvnw spring-boot:run
```

서버 구동 후 브라우저에서 `http://localhost:5531` 주소로 접속 가능합니다.

---

## 🤝 협업 가이드 (Contribution Guide)
본 프로젝트는 일관된 개발 흐름을 유지하기 위해 커밋 메시지 규칙을 준수합니다. 자세한 사항은 [GIT_COMMIT_CONVENTION.md](file:///c:/Users/KOSTA/Desktop/2026.05.KOSTA/letsgo-springboot/GIT_COMMIT_CONVENTION.md) 파일을 참고해 주세요.
