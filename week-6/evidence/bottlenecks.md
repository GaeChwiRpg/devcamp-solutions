# Bottlenecks — 부하 상태에서의 핫스팟 상위 3개

## 측정 조건

- 부하 도구: `hey -n 5000 -c 100 'http://localhost:8080/api/products/search?keyword=sample'`
- 프로파일러: async-profiler (`./profiler.sh -d 60 -f flame.svg <PID>`)
- 시드: 약 10만 row (Week 4 시드 재사용)

도구 선택은 1택 (`MEASUREMENT-OPTIONS.md`의 다른 후보 가능).

## 핫스팟 상위 3개

| # | 메서드 / 위치 | 자체 시간 (cpu samples) | 호출 빈도 | 비고 |
| --- | --- | --- | --- | --- |
| 1 | `ProductRepository.searchByKeyword` (LIKE %kw% 풀스캔 + 결과 무제한) | 41% | 100 rps | 결과를 메모리로 다 싣는 비용이 크다 |
| 2 | `Hibernate hydrate Product entity` | 18% | 100 rps × 결과 N | 1번이 줄면 자연스럽게 줄어든다 |
| 3 | `Tomcat/JSON 직렬화 (ProductResponse 매핑)` | 9% | 100 rps × 결과 N | 응답 size 커서 직렬화 비용. 1번 영향 |

## 우선순위 결정

영향 × 노력 매트릭스로 1순위는 **#1 검색 결과 개수 제한**.

- 영향: 1번이 줄면 #2, #3도 동시에 줄어든다 (caller chain).
- 노력: Pageable 추가 한 줄 + 서비스 1줄 변경.
- vs. #2: hydrate를 줄이려면 projection으로 DTO 변환 필요 — 노력 큼, 학습 부담 큼.
- vs. #3: 응답 직렬화 자체는 1번이 줄면 자동으로 줄어들기 때문에 단독 1순위 아님.

## 다른 도구를 골랐다면

- VisualVM을 골랐다면 GC 압박 같은 메트릭을 더 보기 좋았겠지만, async-profiler가 풀스캔 hotspot을 더 정확하게 보여줘서 우선순위 결정이 빨랐다.
- JFR을 골랐다면 production grade의 long-run 데이터 수집이 가능 (운영 시점 다음 단계).
