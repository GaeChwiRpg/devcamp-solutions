# Concurrency Success Log — pessimistic write lock 적용 후

## 적용

`ProductRepository.findByIdForUpdate` 추가 + `ProductService.decreaseStock`이 그것을 사용:

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("select p from Product p where p.id = :id")
Optional<Product> findByIdForUpdate(@Param("id") Long id);
```

## 같은 시나리오 재실행

- 초기 재고 `stock = 10`, 동시 50 요청, quantity=1.
- 같은 `xargs -P 50` 명령으로 재현.

## 결과

```
  10 200
  40 409
```

- **10개만** 성공, 40개는 409 Conflict (`not enough stock` IllegalStateException).
- 최종 stock 확인:

```bash
$ curl -s http://localhost:8080/api/products/1 | jq .stock
0
```

- stock이 정확히 0. 직렬화 성공.

## 5회 반복 재현

- 5번 같은 시나리오 반복했고, 매번 **`(success=10, conflict=40)` + 최종 stock=0** 동일.
- 락이 맞고 있다는 신호로 본다.

## 부작용 / 한계

- 동시 요청 50개가 직렬화되니 처리 시간이 약 4배 늘었다(p50 latency 21ms → 84ms).
- 핫 row(인기 상품 1개에 트래픽 집중) 시나리오에서는 lock contention이 더 커진다.
- 더 많은 동시성 부하가 예상되면 application-level 분산락(예: Redis SETNX with TTL)이 다음 단계.
