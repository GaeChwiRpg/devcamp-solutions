# 05-week4-index

> 교재: `AI한테 시키기 전에 알아야 할 SQL/DB 초기초` 9장 인덱스 + 본인이 학습한 책 자료

이번 주는 책의 **풀스캔 → 인덱스 → EXPLAIN 으로 검증** 흐름을 본인 코드에서 한 번 손에 익히는 미션입니다. "느려요" 가 아니라 **"rows 100k → 23, latency 800ms → 50ms (-94%)"** 같은 _수치 답변_ 이 되는 주.

## 핵심 학습 포인트 (이번 주 적용 대상)

1. **인덱스의 본질** (책 9장): 전화번호부 = 가나다 정렬 = _미리 정렬해둔 빠른 길_. 풀스캔 (1만 행) vs 인덱스 스캔 (~14회).
2. **EXPLAIN 3 컬럼 (`type` / `key` / `rows`)**: 인덱스가 _존재_ 한다 ≠ 인덱스가 _쓰인다_. EXPLAIN 으로 확인.
   - `type=ALL` + `key=null` → 풀스캔
   - `type=ref` + `key=idx_xxx` → 인덱스 적중
3. **before / after 같은 데이터로 측정** — 데이터·쿼리 다르면 비교 무효. 면접 신뢰 깨짐.
4. **인덱스 _안 타는_ 함정**: 컬럼 가공 (`YEAR(created_at)`), LIKE 앞쪽 와일드카드, OR 조건 한쪽 빠짐, 카디널리티 낮음, 데이터 적은 테이블.
5. **측정 도구 선택 근거**: `curl + time` / `hey` / `k6` / JMeter 중 _trade-off_ 를 본인이 골라서 evidence 에.

## 이번 주에 해야 하는 것

- **baseline 코드의 _느린 조회 API 1개_ 선택** (`project/` 안에 baseline 이미 있음, 환경 세팅 X)
- **인덱스 _없는_ 상태에서 측정** — EXPLAIN + latency (응답 시간) 1회 이상
- **인덱스 추가** (`CREATE INDEX idx_xxx ON ...`) — 본인 선택 근거 evidence
- **인덱스 _있는_ 상태에서 같은 쿼리·같은 데이터로 재측정** — EXPLAIN + latency
- **before / after 비교표 작성** — `rows` 변화 + latency 변화 (p95 또는 평균)
- **측정 도구 선택 근거** evidence — 왜 이 도구 골랐는지 1줄

## 이번 주에 제출할 것

- `report.md`
- `evidence/explain-before.md` — 인덱스 추가 전 EXPLAIN 출력 + 본인 해석 (type / key / rows)
- `evidence/explain-after.md` — 인덱스 추가 후 EXPLAIN 출력 + 본인 해석
- `evidence/latency-comparison.md` — before / after latency 비교표 (단일 수치 X, p95 또는 평균)
- (선택, 권장) `evidence/index-decision-notes.md` — _왜 이 컬럼에 인덱스를 박았는지_ + _측정 도구 선택 근거_
- `project/` 안 실제 코드 — `CREATE INDEX` 마이그레이션 스크립트 또는 schema 변경

## 평가 기준

- EXPLAIN before / after _둘 다_ 있고 `type` / `key` / `rows` 3 컬럼이 보이는가
- 인덱스 선택 _근거_ (어느 컬럼, 왜) 가 evidence 에 있는가 — 단순 "추가했다" X
- before / after 가 _같은 데이터·같은 쿼리_ 로 측정됐는가
- latency 비교가 _단일 측정_ X — p95 또는 평균 + 도구 명시
- _인덱스 안 타는 함정_ (가공/LIKE/OR/카디널리티) 중 1개 이상 본인 코드에서 점검·언급

## 미션 시작 전에 보면 좋은 것

1. 교재 `AI한테 시키기 전에 알아야 할 SQL/DB 초기초` 9장 인덱스
2. 이 README의 "이번 주에 해야 하는 것" + "평가 기준"
3. `project/README.md` — baseline + 미션 흐름 (단계별 가이드 포함)
4. `project/MEASUREMENT-OPTIONS.md` — 측정 도구 후보 메뉴 (EXPLAIN + 응답 시간 각 1택)
5. `evidence/README.md` — 어떤 evidence 를 어떻게 남겨야 하는지
6. `report.md` — 시도/판단/결과/회고 템플릿
7. `docs/review-rubric.md` — AI 리뷰 평가 5축

(정답 PR은 미션 종료 후 운영진이 별도로 공유합니다.)

`project/` 안에 baseline 코드가 이미 들어 있어서 환경 세팅에 시간을 태우지 않습니다. 학생은 그 위에서 인덱스 미션만 다룹니다.

핵심은 "인덱스를 추가했다"가 아니라, **왜 추가했고 무엇이 좋아졌는지 측정 도구 + 수치 + EXPLAIN 3 컬럼으로 증명하는 것**입니다.
