# 09-week8-ai-native

> 교재: `The 10x AI-Native Developer` (7주차 본문 핵심)

이번 주는 "AI를 썼다"가 아니라 **AI 네이티브 개발 사이클의 어디에 어떻게 AI를 썼는지를 본인 흔적으로 증명**하는 미션입니다. 면접 질문 "AI를 어떤 개발 과정에 어떻게 적용했나요?"에 단계별로 답할 수 있어야 통과.

## 미션 두 축

### 축 A. 코딩 단계 6 요소 (필수, 깊이)

책 7주차 04~07장을 본인 코드에 직접 적용:

1. **Context Engineering** — 3 유형 중 본인 미션에 맞는 컨텍스트 패키징
2. **Needle in a Haystack 관리** — 요약 / `claude.md` GPS / `/clear` 리셋 중 1개 전략
3. **페목형제 프롬프트** — 페·목·형·제 4 요소가 다 보이는 프롬프트
4. **`claude.md` 헌법** — 프로젝트 규칙·도메인·제약을 AI에게 주입
5. **`/.claude/commands/` 커스텀 명령** — 반복 작업 자동화 1개
6. **Claude Hooks** — 규칙 위반 자동 차단 1개

### 축 B. 개발 라이프사이클 5 단계 중 **2개 이상** 적용 (필수, 폭)

책 9·10주차에서 다루는 단계별 AI 도구. 학생이 본인 미션 흐름에 맞게 골라서 적용:

| 옵션 | 책 챕터 | 적용 예시 (Week 4 인덱스 미션 기준) |
| --- | --- | --- |
| **1. 기획** | 9주차 03 (Jira MCP) | Jira MCP 또는 GitHub Issue로 미션 분해 + AI에게 티켓 생성 위임 |
| **2. 테스트** | 9주차 06 (Playwright MCP) | Playwright MCP로 E2E 시나리오 자동 생성 (인덱스 적용 전후 응답시간 검증) |
| **3. 리뷰** | 10주차 01 (Claude Actions, CodeRabbit) | 본인 PR에 Claude GitHub Actions Review 또는 CodeRabbit 적용 + 받은 리뷰 메모 |
| **4. 배포·운영** | 10주차 06 (Sentry MCP) | Sentry MCP로 baseline 앱 에러 모니터링 + AI에게 에러 분석 위임 |
| **5. 협업 가속** | 9주차 02·04 (gh CLI, Sub-agents) | Sub-agents로 병렬 작업 또는 gh CLI + AI로 PR 본문 자동화 |

**최소 2개 옵션**을 학생이 골라 본인 환경에서 한 번씩 돌려보고 결과를 evidence에 남깁니다. 단순 메모 X, **실제 도구 실행 + 결과 캡처/로그 + 본인 검증**.

## 이번 주에 해야 하는 것

- 이전 주차 미션(예: Week 4 인덱스, Week 5 동시성, Week 6 프로파일링 중 1개)을 골라 **축 A 6 요소를 적용해 다시** 풀이
- **축 B 5 단계 중 2개 이상**을 본인 워크플로우에 적용
- AI가 잘못 답한 사례 3개 + 검증 루프
- 사람-only 워크플로우 vs AI-native 사이클 적용 워크플로우의 차이를 시간·탐색폭·검증 루프 단위로 비교

## 이번 주에 제출할 것

### 축 A — 코딩 6 요소 (7개 evidence)

| evidence 파일 | 교재 매핑 |
| --- | --- |
| `report.md` | (시도/판단/결과/회고 + 라이프사이클 단계 선택 근거) |
| `evidence/claude-md-snapshot.md` | 4. claude.md 헌법 |
| `evidence/commands-snapshot.md` | 5. Commands |
| `evidence/hooks-config.md` | 6. Hooks |
| `evidence/prompt-pack.md` | 3. 페목형제 |
| `evidence/context-strategy.md` | 1·2. Context + Needle 관리 |
| `evidence/workflow-before-after.md` | 종합 비교 |
| `evidence/failure-cases.md` | 검증 루프 |

### 축 B — 라이프사이클 단계 (선택 옵션 2개 이상)

각 옵션마다 evidence 파일 1개:

- `evidence/lifecycle-planning.md` (옵션 1 골랐다면)
- `evidence/lifecycle-testing.md` (옵션 2 골랐다면)
- `evidence/lifecycle-review.md` (옵션 3 골랐다면)
- `evidence/lifecycle-ops.md` (옵션 4 골랐다면)
- `evidence/lifecycle-collaboration.md` (옵션 5 골랐다면)

각 파일은 다음 4가지를 포함:
1. **사용한 도구** (예: Sentry MCP, Playwright MCP, CodeRabbit)
2. **본인 환경에서 한 작업 흐름** (명령 + 결과 캡처/로그)
3. **사람-only 대비 무엇이 바뀌었는지** (시간 / 발견한 것 / 한계)
4. **이 단계를 고른 근거 1줄**

## 평가 기준

- 6 요소 중 4개 이상이 **실제로 작동한 흔적**이 evidence에 있는가
- **라이프사이클 5 단계 중 2개 이상에 AI 도구가 실제로 작동한 흔적**이 evidence에 있는가
- AI 결과 검증 절차가 명확한가 (failure-cases.md)
- before/after 비교가 시간·탐색폭·실패 패턴 단위로 정량화되었는가
- 페목형제 프롬프트 3개에서 페·목·형·제 4 요소가 다 보이는가
- 라이프사이클 단계 선택 근거가 본인 미션 흐름과 정합한가

## 미션 시작 전에 보면 좋은 것

1. 교재 `The 10x AI-Native Developer` 7주차 04~07장 (필수) + 9·10주차 (라이프사이클 옵션 학습)
2. 이 README의 두 축 + 평가 기준
3. 본인이 다시 풀 이전 미션 폴더 (Week 4·5·6 중 하나)
4. `evidence/README.md` — 어떤 evidence를 어떻게 남겨야 하는지
5. `report.md` 템플릿
6. `docs/review-rubric.md`

(정답 PR은 미션 종료 후 운영진이 별도로 공유합니다.)

## Week 9 팀 프로젝트 연결

Week 9는 라이프사이클 5 단계를 팀이 분담해 적용하는 흐름. Week 8은 **개인이 6 요소 + 2~3 단계를 손에 익혀보는 워밍업**.

핵심은 AI를 사용한 사실이 아니라, **개발 사이클의 어느 단계에 AI를 어떻게 썼고 무엇이 측정 가능하게 좋아졌는지**입니다.
