# Concurrency Failure Log — 락 적용 전

## 시나리오

- baseline의 `POST /api/products/{id}/decrease?quantity=1`
- 초기 재고: `stock = 10`
- 동시 요청: 50개 (예상 결과: 40개 성공 + 10개 OverStock 실패, 최종 stock = 0)

## 재현 명령

```bash
# 측정 도구 1택: curl + xargs -P (MEASUREMENT-OPTIONS.md의 후보 중)
seq 1 50 | xargs -P 50 -I{} \
  curl -s -X POST 'http://localhost:8080/api/products/1/decrease?quantity=1' -o /dev/null -w "%{http_code}\n" \
  | sort | uniq -c
```

## 실제 결과

```
  47 200
   3 409
```

- 200 응답이 50개여야 정상 직렬화인데 **47개**가 통과했다.
- 차감 후 stock 확인:

```bash
$ curl -s http://localhost:8080/api/products/1 | jq .stock
-37
```

- **stock이 -37**. 47번 차감했지만 Product.decreaseStock 검사가 read-modify-write 사이에 무력화됨.

## 원인

- `decreaseStock`이 `findById` → 메모리에서 검사 → `setStock` → flush 흐름.
- 두 트랜잭션이 같은 row를 동시에 select하면 둘 다 `stock=10`을 읽고 각자 -1 → 둘 다 통과.
- 트랜잭션 isolation level이 read committed 수준이라 select는 락을 걸지 않는다.

(학생은 본인 측정 결과로 교체. 도구는 `MEASUREMENT-OPTIONS.md`의 다른 후보를 골라도 됨.)
