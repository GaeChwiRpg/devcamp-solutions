---
mission_id: "04-week3-backend-resume"
week: 3
submission_type: "docs"
status: "sample"
---

# Week 3 Backend Resume — 수치화 패턴 + 문제해결 서사

## 시도

- 사전학습~Week 2 산출물 5개를 이력서 bullet 5개로 변환
- before/after 비교: "사용했다/구현했다" 표현을 "문제 → 시도 → 결과 수치"로 교체
- 수치마다 출처 evidence 파일을 metrics-source.md에 추적
- 문제해결 서사 1개를 1분/3분 답변용 분량으로 풀어쓰기
- 책 4장 11절 수치화 패턴을 표로 정리

## 판단

- 책의 "탈락 이력서 vs 눈에 띄는 이력서" 패턴(1·2장)이 핵심: "Spring Boot 사용했음" → "Week 1에서 게시판 API 구현 + N+1 1건을 fetch join으로 해결, 쿼리 1개로 통합"
- 수치 출처 추적은 미션 evidence가 직결되어야 가능 — Week 1·2의 evidence/n-plus-one-after.md, evidence/test-results.md 등을 직접 인용
- 문제해결 서사 후보 중 Week 2 N+1 케이스를 골랐다 — 변경 감지/연관관계 주인 결정/LAZY 기본 3 요소가 한 미션에서 다 들어가서 분량 충분

## 결과

- before bullet 5개: "Spring Boot 프로젝트 진행", "API 만들어봄", "SQL 조금 해봄" — 책의 탈락 패턴 그대로
- after bullet 5개: 모두 문제 → 시도 → 결과 수치 3 요소 포함 (`evidence/resume-bullets-after.md`)
- metrics-source.md: 5개 수치 모두 evidence 파일 경로 추적 가능
- 문제해결 서사 1분/3분 답변 분량 완성

## 회고

- 책의 핵심 메시지 "수치화는 신뢰의 기본 단위"가 가장 강력. 같은 작업도 수치가 있으면 면접관이 follow-up 질문을 할 자료가 생김
- Week 10 면접 준비에서 problem-solving-story.md를 그대로 재사용 예정
- Week 4 이후 미션부터는 매주 수치 출처를 evidence에 박는 습관 정착

## 제출 파일

- `evidence/resume-bullets-before.md`
- `evidence/resume-bullets-after.md`
- `evidence/metrics-source.md`
- `evidence/problem-solving-story.md`
- `evidence/numerization-pattern.md`
