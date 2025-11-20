# CH3 일정 관리 앱 Develop 과제
- **Spring 9기 Spring 심화 - CH3 일정 관리 앱 과제 (Develop 버전)**  
스프링 부트를 기반으로 회원 + 일정 관리 + 댓글 기능을 제공하는 REST API 서버입니다.  
3 Layer Architecture 기반으로 설계되었으며, JPA 단방향 연관관계를 활용하여 테이블 간 관계를 구현하고  
Cookie / Session을 통한 인증/인가 절차를 포함해 백엔드 애플리케이션의 전반적인 구조를 경험할 수 있는 프로젝트입니다.
<br>

## 📌 프로젝트 소개
사용자는 회원가입, 로그인, 회원정보 수정, 로그아웃, 회원탈퇴를 할 수 있습니다.
그리고 사용자는 일정을 등록, 조회, 수정, 삭제할 수 있으며  
각 일정에 댓글을 작성할 수 있는 CRUD 기반의 일정 관리 서버입니다.

- **일정(Schedule)**: 생성/조회/수정/삭제  
- **댓글(Comment)**: 특정 일정에 댓글 생성  
- **인증/인가**: Cookie & Session 기반 인증  
- **JPA 연관관계**: User ↔ Schedule, User ↔ Comment, Schedule ↔ Comment 단방향 매핑  

<br>

## ⚙️ 주요 기능
👤 회원 (User)
- 회원가입 (POST /api/signup)
- 로그인 (POST /api/login)
- 로그아웃 (POST /api/logout)
- 전체 회원조회 (GET /api/users)
- 단건 회원조회 (GET /api/users/{id})
- 회원정보 수정 (PUT /api/users)
- 회원탈퇴 (DELETE /api/users)

🗓 일정 (Schedule)
- 일정 생성 (POST /api/schedules)
- 전체 일정 조회 (GET /api/schedules)
- 단건 일정 조회 (GET /api/schedules/{id})
- 일정 수정 (PUT /api/schedules/{id})
- 일정 삭제 (DELETE /api/schedules/{id})

💬 댓글 (Comment)
- 댓글 생성 (POST /api/schedules/{id}/comments)
- 전체 댓글 조회 (GET /api/comments)
- 단건 댓글 조회 (GET /api/schedules/{id}/comments)
- 댓글 수정 (PUT /api/schedules/{id}/comments/{id})
- 댓글 삭제 (DELETE /api/schedules/{id}/comments/{id})
<br>

## 👩🏻‍💻 기술 스택
- **Language**: Java 17
- **Framework**: Spring Boot 3.5
- **ORM**: Spring Data JPA
- **Database**: MySQL
- **Security / Auth**: Cookie & Session, Custom Login Filter
- **Validation**: Jakarta Validation
- **IDE**: IntelliJ IDEA
- **Build Tool**: Gradle
- **Documentation / Test**: Postman
<br>

## 🗓 개발 기간
- 2025.11.07 ~ 2025.11.20
<br>

## 🗂 ERD
<img width="2020" height="492" alt="image" src="https://github.com/user-attachments/assets/c32dce98-15fe-4df2-a823-f41dbb2dcede" />
<br>



## 📘 API 명세서
Postman API 문서를 통해 각 API의 요청/응답 예시를 확인할 수 있습니다.  
👉 [API 명세서 바로가기](https://documenter.getpostman.com/view/47338059/2sB3WyKGKL)
<br><br>


## 🤓 트러블슈팅 기록
[트러블슈팅 보러가기](https://remnantcjy.tistory.com/entry/%F0%9F%A4%93-%EC%9D%BC%EC%A0%95-%EA%B4%80%EB%A6%AC-%EC%95%B1-Develop-%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8-%ED%8A%B8%EB%9F%AC%EB%B8%94-%EC%8A%88%ED%8C%85-%F0%9F%92%A5)
