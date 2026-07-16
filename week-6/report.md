---
mission_id: "07-week6-profiling"
week: 6
submission_type: "code"
status: "sample"
---

# Week 6 Profiling

## 시도

- 부하 발생기는 `hey`, 프로파일러는 async-profiler 1택 (`MEASUREMENT-OPTIONS.md` 후보 중).
- baseline의 검색 endpoint (`GET /api/products/search?keyword=...`)에 100 동시 부하 60초.
- flame graph + Actuator metrics로 핫스팟 상위 3개를 잡았다.

## 판단

- 1순위는 `ProductRepository.searchByKeyword` — LIKE `%kw%` 풀스캔 + 결과 무제한.
- 영향 × 노력 매트릭스로 봤을 때 1번을 먼저 고치면 caller chain의 #2(hydrate), #3(직렬화)도 같이 줄어든다.
- Pageable 추가 1줄 + 서비스 1줄 변경이라 노력이 가장 작다.

## 결과

| | p50 | p95 | rps |
| --- | --- | --- | --- |
| before | 612 ms | 1,420 ms | 162 |
| after  |  73 ms |   158 ms | 1,318 |

p95 약 9배 개선. 자세한 내용은 `evidence/before-after-table.md`.

## 회고

- "어디가 느린지"보다 "**어디부터 고칠지**" 우선순위 매기는 훈련이 핵심이었다.
- 1번 수정이 #2/#3까지 자연스럽게 줄여주는 caller chain 효과를 손으로 본 게 가장 크게 남았다.
- LIKE `%kw%` 풀스캔 자체는 여전 — 진짜 검색 부하라면 full-text index 또는 검색 엔진 분리가 다음 단계임을 메모.

## 제출 파일

- `evidence/bottlenecks.md` (상위 3개 + 우선순위)
- `evidence/before-after-table.md` (개선 전후)
- `project/src/main/java/.../domain/product/ProductRepository.java` (Pageable 추가)
- `project/src/main/java/.../service/ProductService.java` (PageRequest 50)
