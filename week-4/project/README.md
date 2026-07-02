# Week 4 Project — Baseline + 인덱스 미션

이 폴더에는 **Spring Boot baseline 코드가 이미 들어 있습니다.** 학생은 환경 세팅 없이 바로 인덱스 튜닝 미션에 들어가면 됩니다.

## 시작 명령

```bash
cd 05-week4-index/project
./gradlew bootRun       # 부팅 확인
./gradlew test          # contextLoads 통과 확인
```

## baseline에서 손볼 곳

- `domain/product/Product.java` 또는 SQL 스키마 — 인덱스 추가 후보
- `domain/product/ProductRepository.java` — 쿼리 후보 (`findTop20ByStatusOrderByCreatedAtDesc`, `searchByKeyword`)
- 시드 데이터 — `evidence/seed-data.sql`에 본인이 만든 10만 row 시드를 둔다 (baseline의 `data.sql`은 3 row뿐, 인덱스 효과 측정 불가)

## 측정 도구

도구는 **starter가 정해주지 않습니다.** `MEASUREMENT-OPTIONS.md`를 보고 1택해서 evidence를 남기세요.

## 미션 흐름

1. baseline 부팅 확인
2. 시드 데이터 10만 row 정도 만들기
3. 느린 조회 후보 1~2개 고르기 (latest products / search by keyword)
4. EXPLAIN으로 현재 실행 계획 캡처 (`evidence/before-explain.txt`)
5. 인덱스 후보 적용 (코드 또는 SQL DDL)
6. EXPLAIN과 응답 시간 다시 측정 (`evidence/after-explain.txt`, `evidence/latency-comparison.md`)
7. report.md에 시도/판단/결과/회고
