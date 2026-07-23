# 08-week7-redis

## 이번 주에 해야 하는 것

- 캐시를 붙일 API를 고릅니다.
- 캐시 키 기준을 정합니다.
- 캐시 적용 전후 DB 부하 차이를 비교합니다.

## 이번 주에 제출할 것

- `report.md`
- `evidence/cache-key-notes.md`
- `evidence/hit-rate-report.md`
- `evidence/db-load-comparison.md`
- `project/` 안 실제 코드

## 미션 시작 전에 보면 좋은 것

1. 본인이 학습한 캐시 자료 (Cache Aside / Read Through / Write Through, hit rate 측정)
2. 이 README의 "이번 주에 해야 하는 것" + "평가 기준"
3. `project/README.md` — baseline + 미션 흐름 (캐시 후보 명시)
4. `project/MEASUREMENT-OPTIONS.md` — hit rate 측정 + DB 부하 비교 후보 메뉴 (각 1택)
5. `evidence/README.md` 템플릿
6. `report.md` 템플릿
7. `docs/review-rubric.md`

(정답 PR은 미션 종료 후 운영진이 별도로 공유합니다.)

`project/` 안에 baseline + `@EnableCaching` 활성 환경이 이미 들어 있어 캐시 미션에 바로 들어갈 수 있습니다.

핵심은 **Redis를 썼다는 사실보다, 어디에 왜 붙였고 무엇이 줄었는지 비교하는 것**입니다.
