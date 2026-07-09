# Week 5 측정 도구 후보 (동시성 재현 / 락 전략)

이 메뉴는 **정답이 아닙니다**. 학생이 그 주차에 의식적으로 1택해서 자기 evidence를 남기게 하기 위한 후보 목록입니다.

## 재현해야 하는 것

- baseline 코드의 `POST /api/products/{id}/decrease` 또는 `POST /api/orders`에 **동시 요청을 보내 재고가 음수가 되는 실패 케이스**
- 락 전략 적용 후 **같은 시나리오에서 음수 재고가 사라진다**는 성공 케이스

## 후보 메뉴

### 동시 요청 발생기

| 도구 | 어떨 때 좋은가 | trade-off |
| --- | --- | --- |
| `curl + xargs -P` 또는 `seq \| xargs -P` | bash 한 줄로 시작. 추가 설치 0개 | 응답 검증/통계는 직접 짜야 함 |
| Apache Bench `ab -n 100 -c 50` | 명령 한 줄. 동시 요청 수 제어 직관 | POST body가 같은 요청만 가능 (id 다른 요청 어려움) |
| Java `ExecutorService` 테스트 | 실제 코드 안에서 단위 테스트로 재현 → CI에서 자동 확인 가능 | 테스트 코드 작성 부담 |
| `k6` vus | 시나리오에 다양한 endpoint 섞을 수 있음. 결과 리포트 풍부 | k6 학습 비용 |

### 결과 검증

| 방법 | 어떨 때 좋은가 | trade-off |
| --- | --- | --- |
| `GET /api/products/{id}` 응답에서 stock 확인 | 가장 단순 | 1번에 1개 product만 검증 |
| H2 콘솔에서 `SELECT stock FROM products` | 모든 row 한 번에 봄 | 콘솔에서 캡처가 PNG라 evidence diff에 약함 |
| 테스트 assertion (`assertThat(stock).isGreaterThanOrEqualTo(0)`) | 자동 검증 | 테스트 코드 작성 부담 |

## 추천 조합 (한 가지 풀이)

- 발생기: `seq 1 50 \| xargs -P 50 -I{} curl -X POST 'http://localhost:8080/api/products/1/decrease?quantity=1'`
- 검증: `curl http://localhost:8080/api/products/1` 응답의 `stock` 값
- evidence: `evidence/concurrency-failure-log.md`(락 적용 전), `evidence/concurrency-success-log.md`(락 적용 후), `evidence/lock-strategy-comparison.md`

## evidence에 꼭 남길 것

- 재현 명령(있는 그대로 복붙해서 실행 가능)
- 실패 시 stock 값이 어떻게 변했는지 표(기대 vs 실제)
- 락 전략 적용 후 같은 명령 결과
- **왜 이 락 전략을 골랐는지 1줄** (optimistic vs pessimistic vs DB 분산락 등)
- 다른 후보를 골랐다면 어떻게 달랐을지 1줄
