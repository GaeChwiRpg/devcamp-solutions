# Evidence

이 폴더는 교재 `AI한테 시키기 전에 알아야 할 SQL/DB 초기초` 9장 인덱스가 본인 코드에 적용된 흔적을 남기는 곳입니다. _수치_ + _도구_ + _근거_ 셋이 모두 보여야 통과.

## 측정 자산 (필수)

- `explain-before.md` — 인덱스 추가 _전_ EXPLAIN 출력 + 본인 해석
  - `type` / `key` / `rows` 3 컬럼 명시
  - 어떤 쿼리·어떤 데이터 (행 수) 로 측정했는지 기록
- `explain-after.md` — 인덱스 추가 _후_ 같은 쿼리·같은 데이터로 EXPLAIN
  - `type` / `key` / `rows` 변화 — 풀스캔 (`ALL`) → 인덱스 적중 (`ref`/`range`) 인지
- `latency-comparison.md` — 응답 시간 before / after 비교표
  - 단일 측정 X — p95 또는 평균 + 측정 도구 (`curl + time` / `hey` / `k6` / JMeter) 명시
  - `rows` 감소율 + latency 감소율 둘 다

## 의사결정 자산 (권장)

- `index-decision-notes.md` — _왜 이 컬럼에 인덱스를 박았는지_ + _측정 도구 선택 근거_
  - 단순 단일 컬럼 vs 복합 인덱스 선택 근거
  - 카디널리티 (값의 다양성) / 선택도 (selectivity) 고려 흔적
  - `type=ref` 가 안 나오는 경우 (LIKE/OR/함수 적용 등) 면 그 함정 점검 노트

## 작성 원칙

- "인덱스를 추가했다" 는 사실보다 "**왜 이 컬럼인가 + 무엇이 측정 가능하게 좋아졌는가**" 가 본문의 80% 이상.
- before / after 는 _반드시_ 같은 데이터·같은 쿼리. 시드 데이터 / 부하 패턴 다르면 비교 무효.
- latency 는 _단일 측정 X_ — 부하 도구로 p95 또는 평균 측정. `curl 1 회` 만 박으면 5점 불가.
- 인덱스 _존재_ 한다 ≠ 인덱스가 _쓰인다_. `key` 컬럼이 `null` 아닌지 EXPLAIN 으로 반드시 확인.
- 인덱스가 안 타면 함정 (가공/LIKE/OR/카디널리티) 중 어디서 어긋났는지 evidence 에 기록.

## 자주 막히는 부분

- _부하 도구 선택_: 1 회 측정만 (`curl + time`) 으로는 분산 못 봄 — `hey` 정도부터 동시 요청 + p95 분포 잡힘
- _카디널리티 낮은 컬럼_ 에 인덱스 박기: `WHERE deleted=0` 같은 boolean 류 — 대부분 같은 값이라 인덱스 의미 X
- _시드 데이터 부족_: 데이터 1만 행 미만이면 옵티마이저가 _그냥 풀스캔이 빠르다_ 판단. baseline 의 시드 데이터 (`project/src/main/resources/data.sql`) 충분한지 확인.
