# Week 5 Project — Baseline + 동시성 미션

이 폴더에는 **Spring Boot baseline 코드가 이미 들어 있습니다.** 학생은 환경 세팅 없이 바로 동시성 재현 미션에 들어가면 됩니다.

## 시작 명령

```bash
cd 06-week5-concurrency/project
./gradlew bootRun
```

## baseline에서 동시성 재현 포인트

- `service/ProductService.decreaseStock` — 조회 후 차감이 분리되어 있음 → 동시 요청 시 음수 재고 가능
- `service/OrderService.create` — `decreaseStock` 호출 + `Order` 저장의 묶음

## 측정 도구

도구는 **starter가 정해주지 않습니다.** `MEASUREMENT-OPTIONS.md`를 보고 1택해서 evidence를 남기세요.

## 미션 흐름

1. baseline 부팅 확인
2. 동시 요청 발생기 1택 (`xargs -P` / `ab` / Java ExecutorService / k6 중)
3. 음수 재고 재현 → `evidence/concurrency-failure-log.md`
4. 락 전략 1택 적용 (`@Lock(PESSIMISTIC_WRITE)` / `@Version` optimistic / 분산락 등)
5. 같은 발생기로 재실행 → `evidence/concurrency-success-log.md`
6. `evidence/lock-strategy-comparison.md`에 후보 비교 + 선택 근거
7. report.md에 시도/판단/결과/회고
