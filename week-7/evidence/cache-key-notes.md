# Cache Key Notes — `latest-active-products`

## 적용 대상

`ProductService.findLatestActiveProducts` (`GET /api/products`).

## 키 / 만료 / 무효화

| | 값 |
| --- | --- |
| cache name | `latest-active-products` |
| key | 고정 `'all'` (조건이 status=ACTIVE / created_at DESC LIMIT 20으로 고정) |
| 만료 (TTL) | 없음 (sample은 invalidation 기반) |
| 무효화 트리거 | `decreaseStock`이 호출될 때 `@CacheEvict(allEntries=true)` |

## 왜 이 endpoint인가

- 같은 결과를 자주 반환 → cache hit 가치 큼.
- 변경 빈도(decreaseStock 호출)가 조회보다 훨씬 적음 → eviction 비용이 작음.
- 결과가 작음(상품 20건) → 메모리 부담 작음.

## 왜 `searchByKeyword`는 안 골랐는가

- 키워드가 임의 문자열 → cache key가 폭발적으로 늘어남.
- 인기 키워드만 효과 있을 텐데, hot keyword 선별 로직이 추가 학습 부담.
- 인기 키워드 중심 캐시는 다음 단계(별도 미션)로 분리.

## stale 가능성 / 다음 단계

- `decreaseStock` 외에 상품 등록/상태 전환 API가 추가되면 그 endpoint에도 `@CacheEvict` 추가 필요.
- 멀티 인스턴스 환경에서는 in-memory simple cache 한계 — Redis로 전환 시 `spring-boot-starter-data-redis` + `spring.cache.type=redis`.
- TTL을 같이 두면 invalidation 누락 시 fallback이 됨 (예: 60초 fallback expiry).

## 다른 후보를 골랐다면

- searchByKeyword + 인기 키워드 limit 캐시 → hot path 방어에 도움. 단, hot keyword 측정 인프라 추가.
- product detail (`GET /api/products/{id}`) endpoint 캐시 → 상세 페이지 트래픽 많을 때 유효.
