# claude.md (헌법) — 본인 미션용 작성본

> 교재 4번 요소: 프로젝트의 헌법. 모든 대화의 시작점.

## 다시 푼 미션

Week 4 인덱스. baseline 위에서 인덱스 후보 평가 → 1개 적용 → before/after 비교까지를 AI 협업으로 다시 풀었다.

## 작성한 CLAUDE.md 본문

```md
# Week 4 인덱스 미션 — 작업 헌법

## 목표

- baseline의 `GET /api/products` 와 `searchByKeyword` 중 1개를 골라 인덱스 튜닝.
- 측정은 `EXPLAIN ANALYZE` + p95 응답시간 둘 다.
- 결과는 `evidence/latency-comparison.md`에 표(쿼리 / 3회 평균 / p95 / scan rows / 인덱스 사용)로 남긴다.

## 도메인 컨텍스트

- 테이블: `products(id, title, sku, status, stock, price, created_at)`
- 핵심 쿼리:
  - `WHERE status='ACTIVE' ORDER BY created_at DESC LIMIT 20`
  - `WHERE status='ACTIVE' AND LOWER(title) LIKE '%kw%' ORDER BY created_at DESC`
- 시드: 약 10만 row, status ACTIVE 비율 약 86%.

## 작업 경계 (절대 어기지 말 것)

- 미션 디렉토리 외 수정 금지. `05-week4-index/` 안에서만 수정.
- `project/` 안에서만 코드 변경. README와 `MEASUREMENT-OPTIONS.md`는 안 건드린다.
- 인덱스를 추가할 때 마이그레이션 파일은 `project/src/main/resources/db/V*__add_*.sql` 형식.
- LIKE `%kw%` 풀스캔을 풀려고 별도 검색엔진 도입은 이번 주 범위 밖.

## 출력 형식

- 답안을 통째로 주지 말고, 단계별 reasoning을 먼저 보여준 뒤 최종 코드를 마지막에.
- 인덱스 후보 비교는 표 형식.
- evidence/*.md는 마크다운 표 + "왜 이걸 골랐는지" 1줄 + "다른 후보를 골랐다면" 1줄 포함.

## 검증 규칙

- AI가 제안한 인덱스 DDL을 받으면 반드시 `EXPLAIN ANALYZE` 결과로 직접 검증한 뒤 적용.
- 응답시간은 hey 부하 명령 결과 3회 평균을 사용. AI가 만들어낸 추정 수치는 사용 금지.
```

## 왜 이 규칙들을 골랐는가

- **"AI가 답을 통째로 주지 말 것"** — 학생 학습 목적이 사라지지 않게.
- **"미션 경로 외 수정 금지"** — mission-guard.yml 가드와 동일선상.
- **"AI 추정 수치 사용 금지"** — 실측 수치만 evidence에 들어가야 reviewer가 신뢰 가능.
- **"단계별 reasoning 먼저"** — 답이 틀렸을 때 reasoning에서 잘못된 가정을 잡기 위함.
- **"도메인 컨텍스트 박아둠"** — 매 프롬프트마다 schema와 시드 분포를 다시 적지 않게.

## 적용 후 효과 (요약)

- 도메인 컨텍스트 6줄을 박아두니, 매 프롬프트가 평균 7~8줄에서 2~3줄로 줄었다.
- "검증 규칙"이 없을 때 AI가 hey latency 추정치를 그럴듯하게 적었다(`failure-cases.md` 사례 1).
- 작업 경계 규칙으로 AI가 main 브랜치 README를 건드리려는 시도 1번을 차단(`hooks-config.md` 사례 1과 연결).
