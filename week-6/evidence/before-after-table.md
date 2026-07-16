# Before / After — 검색 API 응답 시간

## 측정 조건

- 부하 도구: `hey -n 5000 -c 100 'http://localhost:8080/api/products/search?keyword=sample'`
- 시드: 약 10만 row (Week 4 시드 재사용)
- 환경: macOS / OpenJDK 17.0.13 / H2 in-memory

## 결과

| | p50 | p95 | rps | err |
| --- | --- | --- | --- | --- |
| before (결과 무제한) | 612 ms | 1,420 ms | 162 | 0% |
| after  (결과 50건 제한) |  73 ms |   158 ms | 1,318 | 0% |

3회 평균. JVM warmup 30초 후.

## 무엇이 줄었는가

- 검색 매칭 후 메모리에 싣는 row 수가 평균 ~14,000 → 50으로 줄었다 (시드 분포 기준).
- hydrate cost (#2)와 직렬화 cost (#3)가 자동으로 줄었다.
- p95가 1,420 ms → 158 ms 약 9배 개선.

## 남는 한계

- LIKE `%keyword%` 풀스캔 자체는 여전. 진짜 검색 부하가 커지면 full-text index 또는 검색 엔진 분리가 다음 단계.
- 결과를 50건으로 자르는 비즈니스 결정은 UX와 같이 보고 다음 주차에 페이지네이션 API로 확장하는 게 자연스럽다.
