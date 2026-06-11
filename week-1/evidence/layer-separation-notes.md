# Layer Separation Notes — 3계층 분리 근거

> 교재 5교시 1·2장: 점원(Controller) / 매니저(Service) / 창고지기(Repository) 책임 분리.

## 본인 코드의 3계층

```
project/src/main/java/co/dingcodingco/week1sample/
├── api/post/                 ← 점원 (Controller)
│   ├── PostController.java       HTTP 요청/응답만 처리
│   ├── PostCreateRequest.java    요청 DTO
│   └── PostResponse.java         응답 DTO
├── service/                  ← 매니저 (Service)
│   ├── PostService.java          비즈니스 흐름 (검증 + 저장 + 후속)
│   └── PostNotFoundException.java
├── domain/post/              ← 창고지기 (Repository) + 도메인
│   ├── Post.java                 도메인 객체
│   └── PostRepository.java       저장만
└── api/common/
    ├── ApiErrorResponse.java     글로벌 예외 응답 모양
    └── GlobalExceptionHandler.java
```

## 책임 경계 결정

### Controller (점원) — 무엇만 한다

- HTTP 요청 입력을 DTO로 받기
- DTO를 Service에 넘기기
- Service 결과를 응답 DTO로 변환
- HTTP 상태 코드 결정

**하지 않는 것**: 비즈니스 검증, DB 호출, 트랜잭션 관리.

### Service (매니저) — 무엇만 한다

- 도메인 검증 (제목 비었나, 본문 길이 등)
- 비즈니스 흐름 조립 (저장 + 카운터 증가 같은 다단계)
- @Transactional 경계 결정
- 도메인 예외 throw

**하지 않는 것**: HTTP 응답 형태, JSON 직렬화, DB 쿼리 직접 호출.

### Repository (창고지기) — 무엇만 한다

- 저장/조회/삭제 메서드 노출
- DB 직접 호출

**하지 않는 것**: 비즈니스 검증, HTTP 직접 호출, 다른 Repository 호출.

## 왜 이렇게 나눴는가 (책 5교시 2장 "이유 3가지")

### 이유 1: 재사용성

만약 모바일 앱용 API가 추가되면, Controller만 새로 만들고 Service는 그대로 재사용. Service가 HTTP 응답을 직접 만들면 이 재사용이 안 됨.

### 이유 2: 책임의 분리 (SRP)

- 컴파일 에러가 났을 때 어느 계층 문제인지 즉시 보임.
- "DB 응답 늦어요" 같은 문의가 오면 Repository 1곳만 보면 됨.

### 이유 3: 테스트의 천국

- Service 테스트는 Repository를 mock하고 비즈니스 흐름만 빠르게 검증 가능.
- Controller 테스트는 MockMvc로 HTTP 입출력만.
- Repository 테스트는 H2 in-memory에서 SQL 동작만.

각각이 독립이라 테스트가 빠르고 견고함.

## Week 2 JPA 전환 대비

이 분리 덕분에 다음 주 JPA로 바꿀 때:
- Repository 인터페이스만 JPA로 교체 (`extends JpaRepository<Post, Long>`)
- Service는 변경 없음 (인터페이스가 같으므로)
- Controller는 당연히 변경 없음

→ 학생이 Week 2에 N+1 같은 JPA 학습에만 집중 가능.

## 학습

책의 비유 "점원이 매니저 일을 하기 시작하면 가게가 망한다"가 **책임 경계 위반의 비용**을 가장 직관적으로 설명. 3계층은 코드 구조가 아니라 **책임 경계 약속**이라는 점이 본 미션의 핵심.
