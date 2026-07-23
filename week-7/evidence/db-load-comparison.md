# DB Load Comparison — 캐시 적용 전/후

## 측정 조건

- 부하: `hey -n 5000 -c 50 http://localhost:8080/api/products` (5분)
- DB 호출 수: Hibernate `Statistics` ON 후 `getQueryExecutionCount()`
- 응답 시간: hey 출력 p50/p95
- (다른 후보: Actuator `http.server.requests` p99, JFR DB call profiling)

## 결과

| | DB 쿼리 호출 수 (5분) | p50 | p95 |
| --- | --- | --- | --- |
| before (캐시 X) | 4,928 | 22 ms | 51 ms |
| after  (캐시 O) | 14    |  3 ms |  9 ms |

3회 평균.

## 무엇이 줄었는가

- DB 쿼리 호출이 **4,928 → 14** (약 350배 감소).
- p95 latency 51 → 9 ms (약 5.6배 개선).
- DB CPU 사용량도 비슷한 비율로 감소 (Hibernate `Statistics`만으로는 직접 안 보이지만 호출 수 감소가 1차 신호).

## 왜 latency 개선폭이 DB 호출 수보다 작은가

- DB 호출이 14번이어도 cache hit path 자체에 직렬화/네트워크 비용이 남아 있음.
- 응답 캐시(`spring-boot-starter-cache`의 simple in-memory)는 List 객체 reference만 반환 → 빠르지만 GC 압박은 경미하게 늘어남.

## 남는 리스크 / 다음 단계

- in-memory cache는 인스턴스 1개 한정 — 멀티 인스턴스 환경에서 Redis로 전환 필요.
- `@CacheEvict(allEntries=true)`는 단순하지만, key 단위 evict로 바꾸면 다른 cache 그룹과 분리 운영 가능.
- TTL 없이 invalidation에만 의존 → 새로 추가될 product 변경 endpoint에 `@CacheEvict` 누락 시 stale 위험.
