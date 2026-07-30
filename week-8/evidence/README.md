# Evidence

이 폴더는 교재 `The 10x AI-Native Developer`의 6 요소가 실제로 작동했는지 증명하는 자산을 모으는 곳입니다.

## 축 A — 코딩 단계 6 요소 (필수)

책 7주차 04~07장. 모두 작성:

- `claude-md-snapshot.md` — 본인이 작성한 CLAUDE.md 본문 + 왜 이 규칙들을 골랐는지
- `commands-snapshot.md` — 본인이 만든 커스텀 Command 1개 정의 + 실제 실행 로그
- `hooks-config.md` — Claude Hook 설정 1개 + 그 hook이 잡아낸 위반 사례
- `prompt-pack.md` — 페르소나·목표·형식·제약 4 요소가 보이는 프롬프트 3개 + 결과 요약
- `context-strategy.md` — 3 유형(코드/파일·문서/이미지) 중 사용한 컨텍스트 + Needle in a Haystack 관리 전략 1개
- `workflow-before-after.md` — 사람-only vs AI-native 워크플로우 비교 (시간·탐색폭·검증 루프·실패 패턴)
- `failure-cases.md` — AI가 잘못 답한 사례 3개 + 어떻게 잡아냈고 어떤 prompt로 수정했는지

## 축 B — 라이프사이클 5 단계 중 **2개 이상** (필수)

책 9·10주차. 학생이 골라서 작성:

| 파일 | 옵션 | 책 챕터 |
| --- | --- | --- |
| `lifecycle-planning.md` | 기획 | 9주차 03 (Jira MCP), AI PRD |
| `lifecycle-testing.md` | 테스트 | 9주차 06 (Playwright MCP), 07 (Browser/Puppeteer MCP) |
| `lifecycle-review.md` | 리뷰 | 10주차 01 (Claude GitHub Actions, CodeRabbit) |
| `lifecycle-ops.md` | 배포·운영 | 10주차 06 (Sentry MCP), 02~07 (Docker, Terraform, AWS) |
| `lifecycle-collaboration.md` | 협업 가속 | 9주차 02 (gh CLI), 04 (Sub-agents), 05 (claude-squad) |

각 파일은 다음 4가지 섹션을 가집니다:

1. **사용한 도구** (구체적 도구명 + 버전 + 설치/설정)
2. **본인 환경에서 한 작업 흐름** (명령 + 결과 캡처 또는 로그)
3. **사람-only 대비 무엇이 바뀌었는지** (시간 / 발견한 것 / 한계)
4. **이 단계를 고른 근거 1줄**

## 작성 원칙

- "AI를 썼다"는 사실보다 "**어느 단계에서 무엇이 측정 가능하게 좋아졌는지**"가 본문의 80% 이상
- prompt-pack.md의 프롬프트 3개는 **페·목·형·제가 어디에 들어갔는지 표시**
- failure-cases.md는 AI가 그럴듯한 거짓말을 했을 때를 포함 (hallucination)
- lifecycle-* 파일은 **실제 도구 실행 흔적**(스크린샷·로그·명령)이 보여야 단순 메모 X
- 6 요소 중 4개 이상 + 라이프사이클 2개 이상이 평가 기준 미달 방지선
