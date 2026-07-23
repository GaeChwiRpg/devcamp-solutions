# Week 7 측정 도구 후보 (Redis 캐시 / 히트율)

이 메뉴는 **정답이 아닙니다**. 학생이 그 주차에 의식적으로 1택해서 자기 evidence를 남기게 하기 위한 후보 목록입니다.

## 측정해야 하는 것

- 캐시 적용 전/후 **DB 부하 차이** (호출 수, p95)
- 캐시 **hit rate** (hit / (hit + miss))
- 캐시 키 선택 근거와 만료 전략

## 후보 메뉴

### 캐시 hit rate 측정

| 도구 | 어떨 때 좋은가 | trade-off |
| --- | --- | --- |
| Spring Actuator `/actuator/metrics/cache.gets` (tag: result=hit/miss) | 코드 변경 0개. Spring Cache 어노테이션만 붙이면 자동 노출 | 캐시 구현이 Spring `CacheManager`를 거쳐야만 잡힘 |
| Redis `INFO stats` (`keyspace_hits`, `keyspace_misses`) | Redis 자체가 보고하는 1차 정보 | Spring 캐시 어노테이션과 결합한 경우 hit가 Spring 단에서 끝나서 Redis까지 안 갈 수도 있음 |
| 직접 카운터 (Micrometer Counter) | 정확히 본인이 원하는 시점에 카운트 | 코드 변경 필요 |

### DB 부하 차이

| 도구 | 어떨 때 좋은가 | trade-off |
| --- | --- | --- |
| Hibernate `Statistics` ON | DB 쿼리 호출 수가 깔끔하게 숫자로 나옴 | 운영 환경에서는 보통 OFF |
| `SHOW STATUS LIKE 'Com_select'` 또는 H2 system table | DB 쪽에서 본 호출 수 | H2와 MySQL이 약간 다름 |
| Actuator `http.server.requests` 응답시간 비교 | 사용자 관점 latency가 어떻게 줄었는지 | DB 호출 수 자체는 안 보임 |

## 추천 조합 (한 가지 풀이)

- 캐시 적용: `@Cacheable("latest-products")` on `ProductService.findLatestActiveProducts`
- 부하: `hey -n 1000 -c 50 http://localhost:8080/api/products`
- hit rate: `curl http://localhost:8080/actuator/metrics/cache.gets?tag=cache:latest-products`
- evidence: `evidence/cache-key-notes.md`, `evidence/hit-rate-report.md`, `evidence/db-load-comparison.md`

## evidence에 꼭 남길 것

- 캐시 key 형식 + 만료 시간 + invalidation 트리거
- 적용 전/후 DB 호출 수 비교 (3회 평균)
- hit rate (5분 부하 후 마지막 1분 기준)
- **왜 이 endpoint에 캐시를 골랐고 다른 곳은 안 했는지 1줄**
- 캐시가 stale해지는 시나리오와 그때 어떻게 할지 1줄
