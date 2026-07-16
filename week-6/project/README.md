# Week 6 Project — Baseline + 프로파일링 미션

이 폴더에는 **Spring Boot baseline 코드가 이미 들어 있습니다.** 학생은 환경 세팅 없이 바로 프로파일링 미션에 들어가면 됩니다.

## 시작 명령

```bash
cd 07-week6-profiling/project
./gradlew bootRun
```

## baseline에서 핫스팟이 생기기 쉬운 곳

- `domain/product/ProductRepository.searchByKeyword` — `LOWER(...) LIKE` 패턴
- `service/OrderService.create` — DB 트랜잭션 + decrease 흐름
- 부하를 더 주려면 시드 데이터를 늘리거나 `searchByKeyword` 키워드를 길게

## 측정 도구

도구는 **starter가 정해주지 않습니다.** `MEASUREMENT-OPTIONS.md`를 보고 부하 발생기와 프로파일러를 각각 1택해서 evidence를 남기세요.

## 미션 흐름

1. baseline 부팅 확인 + Actuator metrics 노출 확인 (`http://localhost:8080/actuator/metrics`)
2. 부하 발생 1택 (`hey` / `wrk` / `k6` 중)
3. 프로파일러 1택 (async-profiler / VisualVM / JFR / JProfiler 중) → `evidence/profile-flamegraph.svg` 등
4. 핫스팟 상위 3개 → `evidence/bottlenecks.md` (메서드 + 자체 시간 + 호출 빈도)
5. 우선순위 결정 (영향 × 노력) → 1번부터 1개 개선
6. 개선 전후 수치 비교 → `evidence/before-after-table.md`
7. report.md에 시도/판단/결과/회고
