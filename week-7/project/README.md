# Week 7 Project — Baseline + 캐시 미션

이 폴더에는 **Spring Boot baseline 코드가 이미 들어 있습니다.** baseline에는 `@EnableCaching`이 켜져 있고 `spring.cache.type=simple`로 시작합니다(Redis 전환은 학생이 직접).

## 시작 명령

```bash
cd 08-week7-redis/project
./gradlew bootRun
```

## baseline에서 캐시를 붙일 후보

- `ProductService.findLatestActiveProducts` — 같은 결과를 자주 반환, 변경은 느림 → 캐시 적합
- `ProductService.searchByKeyword` — 인기 키워드만 캐시 가치, 그 외에는 메모리 낭비
- `OrderService.create` — 캐시 부적합(매 요청마다 상태 변경)

## 측정 도구

도구는 **starter가 정해주지 않습니다.** `MEASUREMENT-OPTIONS.md`를 보고 hit rate 측정 방법과 DB 부하 비교 방법을 각각 1택해서 evidence를 남기세요.

## 미션 흐름

1. baseline 부팅 확인 + Actuator caches endpoint 확인 (`http://localhost:8080/actuator/caches`)
2. 캐시 적용 endpoint 1개 선택, key/만료 전략 결정 → `evidence/cache-key-notes.md`
3. `@Cacheable` 또는 직접 캐시 코드 적용
4. (선택) Redis로 전환 → `spring-boot-starter-data-redis` 의존성 추가
5. 부하 발생 후 hit rate 측정 → `evidence/hit-rate-report.md`
6. DB 호출 수 또는 응답 시간 비교 → `evidence/db-load-comparison.md`
7. report.md에 시도/판단/결과/회고
