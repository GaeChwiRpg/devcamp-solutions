# Prompt Pack — 페목형제 적용 프롬프트 3개

> 교재 3번 요소: 페르소나·목표·형식·제약 4 요소가 모두 보이는 프롬프트.

각 프롬프트는 **[페]** **[목]** **[형]** **[제]** 태그로 4 요소가 어디에 들어갔는지 표시했다.

---

## 프롬프트 1 — 인덱스 후보 비교

```
[페] 당신은 MySQL/H2의 인덱스 튜닝 전문가입니다. 10만 row 규모 OLTP 환경의 검색 쿼리를 자주 다뤘다고 가정합니다.

[목] baseline의 ProductRepository에 있는 다음 쿼리에 대해 인덱스 후보 3개를 비교하고 1개를 추천해 주세요:
  SELECT * FROM products WHERE status='ACTIVE' ORDER BY created_at DESC LIMIT 20;

[형] 표 형식으로:
  | 후보 | 인덱스 정의 | 예상 scan type | 예상 rows | trade-off |
표 아래에 "추천: ___, 이유 1줄" 추가.

[제]
- 풀텍스트 검색이나 외부 검색엔진 도입은 후보에서 제외.
- 인덱스가 INSERT/UPDATE 비용을 늘리는 trade-off도 trade-off 칸에 적을 것.
- 추측이 아니라 일반적인 RDBMS 동작 기준으로 작성. 실측은 내가 직접 EXPLAIN ANALYZE로 검증할 것.
```

### AI 결과 요약

후보 3개 비교: (a) `(status, created_at DESC)`, (b) `(created_at DESC)`, (c) `(price, created_at)`. 추천 (a). 근거: 조건 + 정렬 한 번에 만족.

→ 사람-only로 했다면 후보 2개 정도만 떠올렸을 것. AI가 (c) price 후보를 제안한 게 학습 자산이었다(우리 정렬 키와 안 맞아 탈락이지만 비교 폭이 늘었다).

---

## 프롬프트 2 — EXPLAIN 결과 해석

```
[페] 당신은 H2 query plan을 능숙하게 읽는 시니어 백엔드입니다.

[목] 다음 두 EXPLAIN ANALYZE 출력을 비교하고, 인덱스가 실제로 효과 있었는지 판단해 주세요:

  [before]
  SELECT * FROM PUBLIC.PRODUCTS ... TABLE_SCAN /* scanCount: 100001 */ ...

  [after]
  ... IDX_PRODUCTS_STATUS_CREATED_AT range scan /* scanCount: 21 */ ...

[형] 응답:
  1. 한 줄 결론 (효과 O/X)
  2. 근거 1~2줄
  3. 다음 측정에서 추가로 확인할 항목 1줄

[제]
- 응답시간은 EXPLAIN에 안 보이므로 추측하지 말 것. 응답시간 측정은 별도.
- "100배 빨라졌다" 같은 멋있는 숫자를 만들어내지 말 것.
```

### AI 결과 요약

1. 효과 O.
2. scanCount 100001 → 21, sort 비용 0(인덱스 정렬 활용).
3. p95 응답시간을 hey로 직접 측정해서 EXPLAIN과 함께 봐야 효과 종합 판단.

→ "100배 빨라졌다" 같은 표현이 안 들어옴. 제약 적용 효과.

---

## 프롬프트 3 — evidence 표 작성

```
[페] 당신은 부트캠프 sample PR을 작성하는 멘토입니다. 비전공자 학생이 따라할 수 있는 한 가지 풀이를 보여주는 게 역할입니다.

[목] 측정 결과를 evidence/latency-comparison.md 형식으로 정리해 주세요. 결과:
  - hey -n 1000 -c 50, 3회 평균
  - before: p50 142ms, p95 198ms, scan ~100,000 rows, no index
  - after:  p50  18ms, p95  41ms, scan ~21 rows, idx_products_status_created_at

[형]
  - 측정 조건 (시드 row수, 도구, 환경) 5줄 이내
  - 표 (case / p50 / p95 / scan rows / index used)
  - 인덱스 선택 근거 1단락
  - "다른 도구를 골랐다면" 1단락
  - 남는 리스크/한계 1단락

[제]
- 측정 도구를 정답처럼 적지 말고 "한 가지 풀이"임을 명시.
- p99나 throughput 같은 측정 안 한 지표를 추가하지 말 것.
- 결과 캡처 PNG는 사용 안 함, 텍스트 표만.
```

### AI 결과 요약

`evidence/latency-comparison.md` 초안 1회만에 통과. 한 줄 수정만 직접 했다(p95 단위 ms를 명시).

→ 형식·제약을 박아두니 후처리 시간이 거의 0으로.

---

## 4 요소 적용 효과 (3개 프롬프트 종합)

| 항목 | 페목형제 X | 페목형제 O |
| --- | --- | --- |
| 평균 왕복 횟수 (목표 결과까지) | 3.7회 | 1.3회 |
| 후처리(수동 정리) 시간 | 8~12분 | 1~2분 |
| AI hallucination 발생 | 2회 (응답시간 추정값 만들어냄) | 0회 (제약으로 차단) |

페·목·형·제 4 요소 중 가장 효과 컸던 건 **제약**. AI를 자유롭게 두면 그럴듯한 거짓말이 나옴.
