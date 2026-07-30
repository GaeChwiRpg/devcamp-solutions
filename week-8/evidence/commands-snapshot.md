# Custom Command — `/run-explain` (본인이 만든 1개)

> 교재 5번 요소: 반복 작업을 짧은 명령으로 자동화.

## 만든 이유

Week 4 인덱스 미션에서 후보를 평가할 때마다 다음 4단계를 반복했다:

1. 시드 데이터를 H2 in-memory에 로드
2. EXPLAIN ANALYZE 쿼리 작성
3. 결과 캡처
4. 마크다운 표로 정리

같은 prompt를 7번 입력하다가 자동화 결정.

## 파일 위치

`.claude/commands/run-explain.md`

## 본문

```markdown
---
description: H2 EXPLAIN ANALYZE 결과를 evidence 표 형식으로 정리
allowed-tools: Bash, Read, Write
---

# 페르소나
당신은 SQL 튜닝 전문가입니다.

# 목표
$ARGUMENTS 의 인덱스 후보에 대해 baseline `EXPLAIN ANALYZE` 와 추가 후 `EXPLAIN ANALYZE` 결과를 둘 다 캡처해서 evidence 표 형식으로 출력해 주세요.

# 형식

| 쿼리 | scan type | rows | 인덱스 사용 |
| --- | --- | --- | --- |
| before | ... | ... | ... |
| after  | ... | ... | ... |

표 아래에 "왜 이 인덱스를 골랐는지" 1줄.

# 제약
- baseline EXPLAIN 결과를 만들어내지 말고 반드시 H2 CLI를 호출해서 가져올 것.
- LIKE `%kw%` 풀스캔은 인덱스 한계로 표시하고 회피하려 들지 말 것.
- 결과 파일은 evidence/explain-table-$ARGUMENTS.md 로 저장.
```

## 실제 실행 로그

```
$ /run-explain status_created_at_index

[1/4] H2 CLI 연결 ... OK
[2/4] baseline EXPLAIN ANALYZE 실행 ...
  TABLE_SCAN, scanCount=100001, sort by createdAt
[3/4] CREATE INDEX idx_products_status_created_at 적용 후 EXPLAIN ANALYZE ...
  IDX_PRODUCTS_STATUS_CREATED_AT range scan, scanCount=21
[4/4] evidence/explain-table-status_created_at_index.md 작성 완료

근거 1줄: status 고정 + created_at DESC 정렬이 인덱스 한 번에 끝나는 조합이라 골랐다.
```

## 줄어든 반복

- Custom command 적용 전: 한 후보 평가에 약 6분 (프롬프트 작성 + EXPLAIN 손으로 + 표 정리).
- 적용 후: 약 1분 (`/run-explain status_created_at_index` 입력).
- 인덱스 후보 5개 평가 시: 30분 → 5분.

## 다른 후보를 골랐다면

- `/draft-report` (report.md 자동 초안) — 효과는 있겠지만 학습 가치는 작아 후순위.
- `/seed-100k` (시드 데이터 자동 생성) — 매주 한 번만 실행이라 자동화 가치 낮음.
