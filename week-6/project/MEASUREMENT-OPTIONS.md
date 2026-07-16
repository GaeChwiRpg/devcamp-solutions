# Week 6 측정 도구 후보 (병목 분석 / 프로파일링)

이 메뉴는 **정답이 아닙니다**. 학생이 그 주차에 의식적으로 1택해서 자기 evidence를 남기게 하기 위한 후보 목록입니다. 도구 선택 자체가 학습 자산입니다.

## 측정해야 하는 것

- 부하 상태에서 **어디가 느린지(핫스팟 상위 3개)**
- 우선순위 판단(왜 1번을 먼저 고치는가)
- 1개 핫스팟 개선 전후 수치 비교

## 후보 메뉴

### 부하 발생

| 도구 | 어떨 때 좋은가 | trade-off |
| --- | --- | --- |
| `hey` 또는 `wrk` | 한 줄 명령으로 부하 발생 | 시나리오가 단순한 endpoint에만 좋음 |
| `k6` | 여러 endpoint를 섞은 시나리오 | k6 학습 비용 |

### 프로파일링

| 도구 | 어떨 때 좋은가 | trade-off |
| --- | --- | --- |
| async-profiler | 매우 정확하고 가벼움. flame graph SVG 산출 | macOS에서는 권한 설정 필요 (java security policy) |
| VisualVM | GUI로 직관적. 메서드 별 누적 시간 보기 편함 | macOS Apple Silicon에서 약간 까다로움 |
| Java Flight Recorder (JFR) | OpenJDK에 내장. 별도 설치 0개. JMC로 분석 | JMC 설치는 별도. 결과 파일이 큼 |
| JProfiler | 가장 풍부. heap/cpu/gc 한 화면 | 유료(트라이얼 가능) |

### Spring 측정 보조

| 도구 | 어떨 때 좋은가 | trade-off |
| --- | --- | --- |
| Actuator `/actuator/metrics/http.server.requests` | endpoint 별 평균/P99 즉시 확인 | 메서드 단위 분석은 안 됨 |
| Spring `Micrometer` Timer 직접 추가 | 의심되는 메서드만 정밀 측정 | 코드 변경 필요 |

## 추천 조합 (한 가지 풀이)

- 부하: `hey -n 5000 -c 100 http://localhost:8080/api/products/search?keyword=sample`
- 프로파일링: async-profiler `./profiler.sh -d 30 -f flame.svg <PID>`
- evidence: `evidence/profile-flamegraph.svg`, `evidence/bottlenecks.md`(상위 3개 + 우선순위), `evidence/before-after-table.md`

## evidence에 꼭 남길 것

- 부하 명령 (재현 가능)
- 핫스팟 상위 3개 (메서드 이름 + 자체 시간 + 호출 빈도)
- **왜 1번부터 고치기로 했는지 1줄** (영향 × 노력 매트릭스)
- 1개 개선 후 수치 비교 (3회 평균 또는 p95)
- 다른 도구를 골랐다면 어떤 정보가 더/덜 보였을지 1줄
