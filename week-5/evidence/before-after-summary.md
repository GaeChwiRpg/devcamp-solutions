# Before / After 요약 — Week 5 동시성 락 적용

같은 시나리오(`POST /api/products/1/decrease?quantity=1` × 50 동시 요청)에서 락 적용 전/후 결과 한 줄 요약.

| | 200 응답 | 409 응답 | 최종 stock | 직렬화? |
| --- | --- | --- | --- | --- |
| **before** (no lock) | 47 | 3 | **-37** | 실패 |
| **after** (pessimistic write lock) | 10 | 40 | **0** | 성공 |

5회 반복해서 모두 동일 결과 확인.

자세한 시나리오·재현 명령은 `concurrency-failure-log.md` / `concurrency-success-log.md`, 후보 비교 근거는 `lock-strategy-comparison.md` 참고.
