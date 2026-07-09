# Lock Strategy Comparison

세 가지 후보를 비교한 뒤 **pessimistic write lock**을 골랐다.

## 후보

### A. Pessimistic Write Lock (`@Lock(LockModeType.PESSIMISTIC_WRITE)`)

- **작동**: SELECT … FOR UPDATE. row-level DB lock.
- **장점**: 구현 간단, 재시도 로직 없음, 결과 예측 쉬움.
- **단점**: 대기 시간 증가, DB connection을 트랜잭션 동안 점유.

### B. Optimistic Lock (`@Version` 컬럼 + 충돌 시 재시도)

- **작동**: 행에 version 컬럼, 업데이트 시 version mismatch면 OptimisticLockException → 재시도.
- **장점**: 충돌 적은 환경에서 빠름. DB lock 안 잡음.
- **단점**: 충돌 많은 hot row에서는 재시도가 폭증해 오히려 느림. 재시도 로직 작성 필요.

### C. 분산 락 (Redis SETNX with TTL)

- **작동**: application-level에서 Redis로 mutex.
- **장점**: 여러 인스턴스에서 같은 key를 동시 처리하지 못하게 함. DB 부하 분리.
- **단점**: 인프라 추가, TTL 만료/장애 시 stale lock 위험, Redis 자체가 SPOF.

## 선택: A. pessimistic write lock

- 이유 1: 단일 인스턴스 환경에서 가장 단순하고 결과 검증이 쉽다.
- 이유 2: 실패 케이스가 깨끗하게 409로 끝남(재시도 로직 학습이 다음 단계).
- 이유 3: 학생이 evidence로 결과를 재현 가능한 형태로 남길 수 있다.

## 다른 후보를 골랐다면

- B를 골랐다면: hot row에서 50 동시 요청 중 다수가 OptimisticLockException → 재시도 → 시간 분산 효과는 있지만 throughput은 더 낮을 수 있다.
- C를 골랐다면: 인프라 부담은 있지만 멀티 인스턴스 환경(Week 9 팀 프로젝트 시 가능성) 대비 학습 가치 있음.

## 남는 리스크

- 트래픽이 더 커지면 lock contention으로 사용자 응답 시간이 들쭉날쭉해질 수 있음.
- DB lock 보유 시간이 길어지지 않게 트랜잭션 경계 안에서 IO 호출(외부 API 등)을 피해야 함.
