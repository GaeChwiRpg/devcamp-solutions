# N+1 탐지 가이드 (학습 보조, 답안 X)

이 파일은 N+1을 어떻게 켜서 보는지 단계만 정리한 학습 보조입니다. 학생은 이 가이드를 보고 본인 코드에서 직접 쿼리를 보고 비교를 남기세요.

## 1. Hibernate가 만드는 SQL을 콘솔에 찍기

`application.yml`:

```yaml
spring:
  jpa:
    properties:
      hibernate:
        show_sql: true
        format_sql: true
        use_sql_comments: true
logging:
  level:
    org.hibernate.SQL: DEBUG
    org.hibernate.orm.jdbc.bind: TRACE   # 바인딩 파라미터까지 보고 싶을 때
```

이 옵션은 운영에서는 끄세요. 학습/디버깅 한정.

## 2. N+1 신호

연관 엔티티 1건마다 쿼리가 추가로 한 번씩 더 나갑니다.

```
Hibernate: select * from posts where ...
Hibernate: select * from comments where post_id = 1
Hibernate: select * from comments where post_id = 2
Hibernate: select * from comments where post_id = 3
...
```

게시글 N개에 대해 `select comments` 가 N번 추가로 나가면 N+1입니다.

## 3. 줄이는 방법 후보

| 방법 | 쓸 때 | trade-off |
| --- | --- | --- |
| `@EntityGraph(attributePaths = "comments")` | 단발 조회에서 필요한 경로만 합치고 싶을 때 | join이 추가되어 row가 곱해질 수 있음 (페이징 시 주의) |
| JPQL `fetch join` | 한 쿼리로 다 가져오고 싶을 때 | 페이징과 collection fetch join은 위험(in-memory 페이징 경고) |
| Batch Size (`@BatchSize` 또는 `default_batch_fetch_size`) | 여러 부모 엔티티를 한 번에 묶어서 자식 가져오고 싶을 때 | 쿼리 수는 줄지만 한 쿼리 자체가 무거워질 수 있음 |
| 조회 전용 DTO + JPQL | 도메인 객체 자체를 하이드레이트할 필요가 없는 조회 | 별도 DTO 작성 필요 |

## 4. evidence에 남길 것

- before: N+1이 보이는 SQL 콘솔 출력 일부 (5~10줄이면 충분)
- after: 위 방법 중 1개 적용 후 같은 흐름 SQL
- **왜 그 방법을 골랐는지 1~2줄** (페이징 여부, 데이터 곱 위험 등)
- (선택) 다른 후보를 골랐다면 어떻게 달랐을까 1줄

## 5. 통과 기준 (이번 주차)

- N+1 1건을 정확히 잡아내지 못해도 OK.
- fetch join 또는 `@EntityGraph` 적용 전후의 SQL이 **본인 언어로 어떻게 달라졌는지 설명**되면 통과.
