---
mission_id: "09-week8-ai-native"
week: 8
submission_type: "docs"
status: "sample"
---

# Week 8 AI Native — 코딩 6 요소 + 라이프사이클 단계 적용

## 시도

- 다시 푼 미션: **Week 4 인덱스**.
- 적용한 교재 6 요소: claude.md / Commands / Hooks / 페목형제 / Context (코드+파일) / Needle 관리(`claude.md` GPS + `/clear`).
- 작업 시간: 약 1시간 30분 (사람-only 약 3시간 30분 대비).

## 판단

- Week 4를 고른 이유: 측정·수치가 명확해서 AI hallucination을 가장 깔끔하게 잡아낼 수 있는 미션.
- claude.md를 가장 먼저 작성한 이유: 도메인 schema와 검증 규칙을 박아두지 않으면 매 prompt에 schema를 다시 적게 됨.
- Hooks를 단 1개만 만든 이유: 작업 경계 위반은 가장 큰 위험이고, 다른 hook(예: 풀스캔 차단)은 학습 효과 없는 마이크로 관리.
- 페목형제 4 요소 중 **제약**에 가장 무게 — AI hallucination 차단의 핵심.

## 결과

- 시간 비교: 사람-only 약 210분 → AI-native 약 90분 (-57%)
- 탐색 폭(고려한 인덱스 후보 수): 2 → 3
- AI hallucination 발견 수: 2회 (모두 검증 단계에서 차단, evidence에는 미반영)
- 작업 경계 위반 시도: 1회 (PreToolUse hook이 차단)
- 페목형제 prompt 평균 왕복 횟수: 3.7회 → 1.3회

## 회고

### 가장 효과 컸던 것

**페목형제의 [제약]**. 응답시간 추정값을 만들어내지 말 것, 정렬 키 충족 안 되는 후보 제외할 것 같은 제약 1줄이 prompt 왕복을 절반 이하로 줄였다.

### 가장 효과 작았던 것

**전략 1 (요약)**. 이번 미션은 4시간 세션이라 요약까지 갈 필요 없었다. 8시간+ 세션에서 다시 평가 예정.

### 다음 미션에 그대로 가져갈 워크플로우

- claude.md에 도메인 schema + 검증 규칙 박기 (매주)
- 측정 자동화 command 1개씩 만들기 (Week 5는 동시성 발생기, Week 6은 부하 명령)
- PreToolUse hook 1개 (`check-mission-scope.sh`)는 그대로 재사용
- 페목형제 prompt 작성 시 "제약" 칸을 가장 먼저 채우기

### 빼야 할 부분

- AI에게 후보 5개 비교 요청 → 노이즈만 커짐. 처음부터 제약으로 후보를 좁히고 시작.

## 제출 파일

- `evidence/claude-md-snapshot.md` — 본인 작성 CLAUDE.md 본문 + 규칙 선택 근거
- `evidence/commands-snapshot.md` — `/run-explain` 커스텀 command + 실행 로그
- `evidence/hooks-config.md` — PreToolUse hook 설정 + 잡힌 위반 사례
- `evidence/prompt-pack.md` — 페목형제 적용 prompt 3개 + 4 요소 위치 표시
- `evidence/context-strategy.md` — 사용한 컨텍스트 유형 + Needle 관리 전략
- `evidence/workflow-before-after.md` — 사람-only vs AI-native 정량 비교
- `evidence/failure-cases.md` — AI 실패 사례 3개 + 검증 루프 4단계
