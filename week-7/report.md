---
mission_id: "08-week7-redis"
week: 7
submission_type: "code"
status: "sample"
---

# Week 7 Redis (캐시)

## 시도

- 캐시 후보 중 `findLatestActiveProducts`를 골랐다 — 같은 결과를 자주 반환하고 변경 빈도가 적어 캐시 적합도가 가장 높음.
- 키는 고정 `'all'` (조건이 status=ACTIVE / created_at DESC LIMIT 20으로 고정).
- 만료 전략: TTL 없이 `@CacheEvict(allEntries=true)`를 `decreaseStock`에 붙임 (sample은 invalidation 기반).

## 판단

- `searchByKeyword`는 키워드가 임의 문자열이라 cache key 폭발 위험 → 후순위.
- product detail은 후보지만 baseline에 detail endpoint 없음 → 다음 단계.
- 캐시 backend는 `spring.cache.type=simple` (in-memory). Redis 전환은 multi-instance 환경 시 다음 단계로 메모.

## 결과

| | DB 쿼리 호출 수 | p50 | p95 | hit rate |
| --- | --- | --- | --- | --- |
| before | 4,928 | 22 ms | 51 ms | — |
| after  |    14 |  3 ms |  9 ms | 99.7% |

자세한 내용은 `evidence/db-load-comparison.md`, `evidence/hit-rate-report.md`.

## 회고

- 캐시 가치는 "read 빈도 / write 빈도" 비율에 압도적으로 비례한다는 것을 손으로 확인했다.
- invalidation 누락(다른 product 변경 API에 `@CacheEvict` 안 붙이기)이 stale의 가장 큰 위험 — 새 endpoint 추가 시 체크리스트 필요.
- TTL과 invalidation을 같이 쓰는 게 실무에서 더 안전 — TTL이 fallback 역할.

## 제출 파일

- `evidence/cache-key-notes.md` (key/만료/무효화 + 다른 후보)
- `evidence/hit-rate-report.md` (Actuator metrics 기반)
- `evidence/db-load-comparison.md` (DB 호출 수 + p95)
- `project/src/main/java/.../service/ProductService.java` (@Cacheable + @CacheEvict)
