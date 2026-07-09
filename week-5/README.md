# 06-week5-concurrency

## 이번 주에 해야 하는 것

- 경쟁 상태를 재현합니다.
- 락 전략 하나를 골라 적용합니다.
- 실패 로그와 성공 로그를 같이 남깁니다.

## 이번 주에 제출할 것

- `report.md`
- `evidence/concurrency-failure-log.md`
- `evidence/concurrency-success-log.md`
- `evidence/lock-strategy-comparison.md`
- `project/` 안 실제 코드

## 미션 시작 전에 보면 좋은 것

1. 본인이 학습한 동시성 자료 (트랜잭션 격리성 / 락 / 비관적 vs 낙관적)
2. 이 README의 "이번 주에 해야 하는 것" + "평가 기준"
3. `project/README.md` — baseline + 미션 흐름 (재현 포인트 명시)
4. `project/MEASUREMENT-OPTIONS.md` — 동시 요청 발생기 후보 메뉴 (1택)
5. `evidence/README.md` 템플릿
6. `report.md` 템플릿
7. `docs/review-rubric.md`

(정답 PR은 미션 종료 후 운영진이 별도로 공유합니다.)

`project/` 안에 baseline 코드가 이미 들어 있고, `decreaseStock`/`OrderService.create`가 동시성 재현 포인트입니다. 학생은 그 위에서 동시성 미션만 다룹니다.

핵심은 **락 이름을 외우는 게 아니라, 어떤 버그를 어떻게 재현하고 막았는지 설명하는 것**입니다.
