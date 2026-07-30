# Claude Hooks — 규칙 위반 자동 차단

> 교재 6번 요소: 자동화된 내부 감사관.

## 설정한 hook (1개)

`.claude/settings.json`:

```json
{
  "hooks": {
    "PreToolUse": [
      {
        "matcher": "Edit|Write",
        "hooks": [
          {
            "type": "command",
            "command": "bash .claude/hooks/check-mission-scope.sh"
          }
        ]
      }
    ]
  }
}
```

## hook 스크립트 본문

`.claude/hooks/check-mission-scope.sh`:

```bash
#!/usr/bin/env bash
# 학생 미션 디렉토리 외 수정 차단.
# 입력: stdin으로 tool input JSON. file_path가 현재 미션 폴더 안에 있어야 통과.

set -euo pipefail

INPUT="$(cat)"
FILE_PATH="$(echo "$INPUT" | jq -r '.tool_input.file_path // empty')"

# 현재 미션 (CLAUDE.md frontmatter나 작업 디렉토리에서 추론).
MISSION_DIR="$(echo "$FILE_PATH" | awk -F/ 'NF >= 2 {print $1; exit}')"

case "$MISSION_DIR" in
  05-week4-index)
    exit 0
    ;;
  "")
    exit 0
    ;;
  *)
    echo "BLOCKED: 현재 미션 폴더(05-week4-index/) 외부 수정 시도" >&2
    exit 2
    ;;
esac
```

## 잡힌 위반 사례 1건

작업 중반에 AI에게 "메인 README에도 인덱스 미션 안내 한 줄 추가해줘"라고 요청. AI가 `README.md`(루트)를 수정하려고 시도. PreToolUse hook이 차단:

```
PreToolUse PreToolUse blocking error from check-mission-scope.sh:
BLOCKED: 현재 미션 폴더(05-week4-index/) 외부 수정 시도
```

→ AI가 "현재 미션 디렉토리 외부 수정이 차단되었습니다. 미션 폴더 안 README에 추가하거나, 사용자 직접 main의 README를 업데이트하시겠어요?"로 응답. 작업 경계 유지됨.

## 다른 후보를 검토했지만 안 만든 hook

- **PostToolUse + Bash filter**: `git push --force` 차단. Week 4는 force push 시나리오 없어 다음 단계로.
- **SessionStart hook**: 세션 시작 시 baseline schema dump 자동 표시. 없어도 CLAUDE.md에 schema 박혀 있어서 가치 작음.

## 효과

- 미션 경로 외 수정 시도 1건 차단.
- AI가 "이걸 main에 푸시하면 안 될까요?" 같은 위험한 제안을 자기 검열하기 시작 (3회 정도).
- mission-guard CI가 PR 단계에서 잡는 것을 작업 시점에서 미리 잡음 → 시간 절약.
