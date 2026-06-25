# Numerization Pattern — 수치화 비교 표

> 교재 1교시 4장 11절 "수치화 패턴" 적용.

## 책의 원칙

> "사용했다 / 만들었다" 류 표현은 면접관이 follow-up 할 거리가 없다.
> "**X를 했더니 Y가 Z만큼 개선되었다**" 형식이 신뢰의 기본 단위.

## 본인 이력서 변환표

### 1. Spring Boot API

| | |
| --- | --- |
| Before | "Spring Boot로 게시판 API를 구현했다." |
| After | "Spring Boot 게시판 API 4개 endpoint를 Controller/Service/Repository 3계층으로 분리해 구현했고, 서비스 단위 테스트 3개로 success/not-found/validation-error 케이스를 커버했다." |
| 수치 출처 | `02-week1-spring-boot/evidence/test-results.md` |

### 2. JPA N+1 개선

| | |
| --- | --- |
| Before | "JPA로 리팩토링하고 N+1을 개선했다." |
| After | "Post 1:N Comment 연관관계에서 LAZY 기본으로 N+1을 의식적으로 노출시킨 뒤, `@EntityGraph` 적용으로 select 호출을 11회 → 1회로 줄이고 응답 시간을 80→25ms로 개선했다." |
| 수치 출처 | `03-week2-jpa/evidence/n-plus-one-after.md` |

### 3. 트랜잭션

| | |
| --- | --- |
| Before | "트랜잭션을 적용했다." |
| After | "@Transactional을 Service 묶음 메서드에만 적용하는 결정 근거를 evidence로 정리하고, 단일 read에는 안 붙임으로써 트랜잭션 시작/커밋 비용을 약 60% 회피했다." |
| 수치 출처 | `02-week1-spring-boot/evidence/transactional-snapshot.md` |

### 4. 변경 감지

| | |
| --- | --- |
| Before | "JPA 변경 감지를 사용했다." |
| After | "`Post.update()` 도메인 메서드 호출만으로 트랜잭션 종료 시 자동 UPDATE 발행 — `save()` 호출 코드 라인 평균 2줄을 0줄로 줄이고 service 코드 가독성을 높였다." |
| 수치 출처 | `03-week2-jpa/evidence/dirty-checking-snapshot.md` |

### 5. SQL/DB 기초

| | |
| --- | --- |
| Before | "SQL을 공부했다." |
| After | "Todo 테이블 설계 + 복합 인덱스 적용 후 EXPLAIN 결과로 type=ALL → type=range 전환을 직접 검증했고, AI가 만든 EXPLAIN 해석에서 잘못된 'type=ref' 주장 1건을 잡아내 수정했다." |
| 수치 출처 | `00-onboarding-sql-db-basics/evidence/ai-verification.md` |

## 책의 5가지 수치화 형식 (1교시 4장 11절 응용)

| 형식 | 예시 |
| --- | --- |
| Before/After 수치 비교 | p95 198→41ms (4.8배) |
| 절대 수치 + 환경 조건 | 시드 10만 row + 동시 50 요청 환경에서 p95 41ms |
| 호출 횟수 / 빈도 | select 11회 → 1회 (`@EntityGraph` 적용) |
| 비율 / 비중 | 코드 라인 60% 감소 |
| 시간 단축 | 한 후보 평가 6분 → 1분 (`/run-explain` 자동화) |

본인 이력서의 모든 수치는 위 5 형식 중 하나에 속하도록 통일.

## 학습

- 수치화는 단순 숫자가 아니라 **검증 가능한 출처 + 환경 조건**이 같이 가야 신뢰
- 면접관 입장에서 "어떻게 측정했나?"라는 follow-up이 오면 evidence 파일을 그 자리에서 보여줄 수 있어야 함
- Week 4 이후 미션의 모든 수치도 같은 방식으로 evidence/metrics-source.md에 추적
