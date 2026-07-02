---
mission_id: "05-week4-index"
week: 4
submission_type: "code"
status: "sample"
---

# Week 4 Index — EXPLAIN + 인덱스로 _수치_ 답변

## 시도

- baseline의 `GET /api/products` (최신 ACTIVE 상품 20건)를 튜닝 대상으로 골랐다.
- 시드 데이터를 약 10만 row로 늘려서 인덱스 효과가 실제로 측정되는 환경을 만들었다.
- 측정 도구는 EXPLAIN은 H2 raw `EXPLAIN ANALYZE`, 응답 시간은 `hey`로 골랐다 (`MEASUREMENT-OPTIONS.md`의 후보 중 1택).

## 판단

- 조건이 `status = 'ACTIVE'` 고정 + `ORDER BY created_at DESC LIMIT 20` 이라 (status, created_at DESC) 복합 인덱스가 가장 자연스럽다.
- 단일 (created_at) 인덱스로는 status 필터 후 정렬이 인덱스 한 번에 안 끝난다.
- (price, created_at) 같은 다른 후보는 우리 정렬 키와 안 맞는다.

## 결과

| | p50 | p95 | scan rows | 인덱스 |
| --- | --- | --- | --- | --- |
| before | 142 ms | 198 ms | ~100,000 | — |
| after  |  18 ms |  41 ms | ~21       | idx_products_status_created_at |

자세한 내용은 `evidence/latency-comparison.md`.

## 회고

- "왜 그 인덱스인지"를 한 번이라도 다른 후보(단일 vs 복합 vs 다른 컬럼 조합)와 비교하고 적은 게 가장 학습이 됐다.
- searchByKeyword는 `%keyword%` 패턴이라 일반 인덱스 한계 — Week 7 캐시 또는 별도 검색 엔진이 다음 단계로 보인다.
- 인덱스 추가는 INSERT/UPDATE 비용을 올리므로, 쓰기 빈도가 큰 테이블에는 신중히 평가해야 한다는 점을 정리했다.

## 제출 파일

- `evidence/seed-data.sql`
- `evidence/explain-before.txt`
- `evidence/explain-after.txt`
- `evidence/latency-comparison.md`
- `project/src/main/resources/db/V2__add_product_indexes.sql`
