---
mission_id: "06-week5-concurrency"
week: 5
submission_type: "code"
status: "sample"
---

# Week 5 Concurrency

## 시도

- baseline의 `POST /api/products/{id}/decrease`를 동시성 재현 대상으로 골랐다.
- 동시 요청 발생기는 `seq | xargs -P 50` 1택 (`MEASUREMENT-OPTIONS.md` 후보 중).
- 초기 재고 10에 50 동시 요청을 보내 락 적용 전/후 결과를 비교했다.

## 판단

- baseline의 `decreaseStock`이 `findById` → 메모리 검사 → `setStock` 흐름이라 두 트랜잭션이 같은 row를 동시 select 시 둘 다 통과 → stock 음수 발생.
- 락 후보 3개(pessimistic write / optimistic + 재시도 / 분산락)를 비교한 뒤 **pessimistic write lock**을 골랐다 (`evidence/lock-strategy-comparison.md`).
- 이유: 단일 인스턴스 환경에서 구현이 가장 단순하고 결과 검증이 깨끗하다.

## 결과

- 락 전: 50 요청 중 47 통과 + stock = **-37** (직렬화 실패)
- 락 후: 50 요청 중 10 통과 + 40 409 + stock = **0** (직렬화 성공)
- 5회 반복 재현해서 동일 결과 확인.

## 회고

- 락은 정확성을 잡지만 latency가 약 4배 늘었다(p50 21→84ms). throughput과 정확성의 trade-off를 처음으로 손으로 만져본 미션.
- hot row 환경에서는 optimistic + 재시도가 더 나쁠 수 있다는 것도 후보 비교에서 정리했다.
- 트랜잭션 경계 안에서 외부 API 호출 같은 IO를 하지 않는 게 락 보유 시간을 짧게 유지하는 원칙이라는 것을 메모.

## 제출 파일

- `evidence/concurrency-failure-log.md`
- `evidence/concurrency-success-log.md`
- `evidence/lock-strategy-comparison.md`
- `project/src/main/java/.../domain/product/ProductRepository.java` (findByIdForUpdate)
- `project/src/main/java/.../service/ProductService.java` (락 적용)
