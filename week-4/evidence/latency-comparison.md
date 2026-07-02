# Latency Comparison — `GET /api/products`

## 측정 조건

- 시드: `evidence/seed-data.sql` (약 10만 row, status ACTIVE 약 86%)
- 측정 도구: `hey -n 1000 -c 50 http://localhost:8080/api/products`
- (도구 선택은 한 가지 풀이. `MEASUREMENT-OPTIONS.md`의 다른 후보를 골라도 된다.)
- 환경: macOS / OpenJDK 17.0.13 / H2 in-memory (MySQL 호환 모드)

## 결과

| | p50 | p95 | rows scanned (avg) | index used |
| --- | --- | --- | --- | --- |
| before | 142 ms | 198 ms | ~100,000 | — (table scan) |
| after  |  18 ms |  41 ms | ~21       | idx_products_status_created_at |

3회 평균 (5분 간격, JVM warmup 30초 후).

## 인덱스 선택 근거

- 조회 조건이 `status = 'ACTIVE'` 고정 + `ORDER BY created_at DESC LIMIT 20` 이라 (status, created_at DESC) 복합 인덱스가 잘 맞는다.
- 단일 (created_at) 만으로는 `status` 필터 후 정렬이 인덱스 한 번으로 안 끝난다.
- (price, created_at) 같은 다른 후보는 LIMIT 정렬 결과가 안 깨끗.

## 다른 도구를 골랐다면

- `wrk` 를 골랐다면 더 정밀한 latency 분포(히스토그램)를 볼 수 있었지만 명령 한 줄로 끝나지 않음.
- DBeaver Explain Plan을 골랐다면 트리 시각화는 좋지만 latency 직접 측정 안 됨 → hey 또는 wrk 와 같이 써야 함.

## 남는 리스크 / 한계

- `searchByKeyword` 는 `%keyword%` 패턴이라 일반 인덱스가 안 잡힌다. Week 7 캐시 또는 검색 엔진 분리가 다음 단계.
- 인덱스 추가는 INSERT/UPDATE 비용을 약간 올린다. 쓰기 빈도에 따라 trade-off.
