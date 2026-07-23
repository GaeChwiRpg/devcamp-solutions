# Cache Hit Rate Report

## 측정 방법

- 부하: `hey -n 5000 -c 50 http://localhost:8080/api/products`
- hit rate: Spring Actuator `/actuator/metrics/cache.gets?tag=cache:latest-active-products`
- (다른 후보: Redis `INFO stats`. 단 sample은 `spring.cache.type=simple` in-memory라 Actuator만 사용.)

## 결과

5분 부하 중 마지막 1분 metric 발췌:

```
{
  "name": "cache.gets",
  "measurements": [{"statistic": "COUNT", "value": 4928.0}],
  "availableTags": [
    {"tag": "result", "values": ["hit", "miss"]},
    {"tag": "cache",  "values": ["latest-active-products"]}
  ]
}
```

result=hit / result=miss 분리 측정:

| | count |
| --- | --- |
| hit  | 4,915 |
| miss | 13    |

**hit rate = 4915 / (4915 + 13) ≈ 99.7%**

## 왜 99% 인가

- decreaseStock 호출이 부하 기간 중 13번 정도 (다른 부하 endpoint 비율). 그때마다 `@CacheEvict` → 다음 첫 요청이 miss → 그 뒤로 다시 hit.
- 만약 decreaseStock과 product list가 비슷한 비율로 호출되면 hit rate가 떨어짐 — invalidation 빈도와 read 빈도의 비율이 핵심.

## 학생 본인 환경에서

- 부하 도구·hit rate 측정 도구는 `MEASUREMENT-OPTIONS.md`의 후보 중 1택.
- Redis 전환했다면 `INFO stats`의 keyspace_hits/misses로 비교해서 Spring Cache 단에서 Redis까지 가는지/Spring Cache가 흡수하는지를 같이 보면 학습이 더 깊어짐.
