# Week 4 측정 도구 후보 (인덱스 / EXPLAIN)

이 메뉴는 **정답이 아닙니다**. 학생이 그 주차에 의식적으로 1택해서 자기 evidence를 남기게 하기 위한 후보 목록입니다. 어떤 걸 골라도 됩니다. 단, 왜 골랐는지 한 줄은 `evidence/` 안에 남기세요.

## 측정해야 하는 것

- before/after **EXPLAIN** 결과 (rows, type, extra, key 사용 여부)
- before/after **응답 시간** (3회 평균 또는 p95)

## 후보 메뉴

### 실행 계획(EXPLAIN)

| 도구 | 어떨 때 좋은가 | trade-off |
| --- | --- | --- |
| `EXPLAIN ANALYZE` raw SQL (H2/MySQL CLI) | 가장 단순. 결과를 `.txt`로 그대로 evidence에 둘 수 있음 | 출력 포맷이 도구별로 달라 비교가 손맛. 학생은 raw 결과를 직접 읽어야 함 |
| DBeaver "Explain Plan" | GUI로 트리 형태 시각화. 비전공자가 트리 모양으로 이해 쉬움 | DBeaver 설치/연결 부담. 캡처가 PNG라 git diff에 안 보임 |
| IntelliJ Database Tool | IDE 안에서 한 화면에 끝남. SQL 작성과 EXPLAIN을 같이 함 | IntelliJ Ultimate가 깔끔하게 쓰기 편함. Community는 제한 |

### 응답 시간

| 도구 | 어떨 때 좋은가 | trade-off |
| --- | --- | --- |
| `curl + time` | 추가 설치 0개. 한 번 실행에 한 줄 | 통계가 빈약(평균 직접 계산), 동시 요청에 약함 |
| `hey` | 명령 한 줄로 동시 요청 + p50/p95/p99 자동 산출 | 별도 설치 (`brew install hey`) |
| `wrk` | 매우 가볍고 정확. Lua 스크립트로 시나리오 변경 가능 | Lua 스크립트 학습 부담 |
| `k6` | JS로 시나리오 작성. 결과 리포트 풍부 | k6 자체 학습 비용 약간. 결과 해석에 약간의 학습 |
| JMeter | GUI로 시나리오 구성. 대형 부하에 강함 | overkill. Week 4에서는 무거움 |

## 추천 조합 (한 가지 풀이)

비전공자가 막힐 때 따라할 수 있는 한 가지 조합. **이게 정답이라는 뜻은 아닙니다.**

- EXPLAIN: `EXPLAIN ANALYZE` raw → `evidence/before-explain.txt`, `evidence/after-explain.txt`
- 응답 시간: `hey -n 1000 -c 50 http://localhost:8080/api/products` → `evidence/latency-comparison.md`에 표로

## evidence에 꼭 남길 것

- 측정 조건: 시드 데이터 row 수, 동시 요청 수, JVM 옵션
- before/after 표 (3회 평균 또는 p95)
- **왜 이 도구를 골랐는지 1줄**
- 다른 후보를 골랐다면 어떻게 달랐을지 1줄(rubric "기술 포인트 적용" 5점 가산)
