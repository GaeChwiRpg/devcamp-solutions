# 문제해결 서사 — Week 2 N+1 해결 (면접 답변용)

> 교재 1교시 1·2·3장 "면접관이 눈여겨보는 경험" + "문제해결능력을 보여주기".

## 1분 답변

> Week 2 JPA 미션에서 게시글 목록 조회 API에 LAZY 연관관계를 두었더니, Hibernate `show_sql` 콘솔에서 N+1 패턴이 보였습니다. 게시글 N개당 댓글 select N번이 추가로 나갔습니다. `@EntityGraph(attributePaths="comments")` 적용 후 쿼리 1개로 통합되었고, 동일 시나리오에서 select 호출 수가 11회에서 1회로 줄었습니다. fetch join 대신 `@EntityGraph`를 고른 이유는 Spring Data JPA의 메서드 시그니처 그대로 두면서 적용 가능했기 때문입니다.

(약 200자, 정확히 1분 분량)

## 3분 답변

> Week 2에서 Week 1 게시판 코드를 JPA 기반으로 리팩토링하면서 Post 1 : N Comment 연관관계를 추가했습니다.
>
> 책에서 권고하는 대로 모든 연관관계를 LAZY 기본으로 두었는데, 이게 문제 신호를 잡는 안전망 역할을 했습니다. 게시글 목록 조회 API를 Postman으로 호출하고 콘솔 SQL 로그를 봤더니, 게시글 10건 조회 후 댓글을 가져오는 select가 10번 더 나가는 N+1 패턴이 보였습니다.
>
> 원인은 Service에서 `posts.forEach(p -> p.getComments())`를 호출할 때마다 LAZY 프록시가 초기화되며 추가 쿼리가 발생한 것이었습니다.
>
> 해결 후보로 fetch join, `@EntityGraph`, `@BatchSize` 세 개를 비교했고 `@EntityGraph(attributePaths="comments")`를 골랐습니다. 이유는 Spring Data JPA의 메서드 시그니처(`findAllByOrderByCreatedAtDesc()`)를 그대로 두면서 어노테이션만 추가하면 되었기 때문이고, fetch join은 페이징과 충돌할 위험이 있었습니다.
>
> 적용 후 같은 시나리오에서 select 호출이 11회 → 1회로 줄었고, 응답 시간도 평균 80ms → 25ms로 개선되었습니다.
>
> 이 경험에서 가장 큰 학습은, JPA의 영속성 컨텍스트와 LAZY 기본 원칙을 알고 있어야 N+1을 의식적으로 잡을 수 있다는 점이었습니다. LAZY를 의도적으로 둬서 문제 신호가 보이게 만든 게 핵심이었습니다.

(약 600자, 정확히 3분 분량)

## Follow-up 예상 질문

| 질문 | 답변 요지 |
| --- | --- |
| 왜 fetch join이 아니라 `@EntityGraph`인가? | 페이징과 collection fetch join은 in-memory 페이징 경고 발생 위험. 또 Spring Data JPA 메서드 시그니처를 보존 가능. |
| `@BatchSize`는 어떨 때 좋은가? | 여러 부모 엔티티의 자식을 묶어서 IN 쿼리 1개로 가져옴. 페이징 친화적. 단 한 쿼리가 무거워질 수 있음. |
| LAZY를 안 두면 어떻게 되나? | EAGER 기본은 N+1을 숨김. 처음에는 빠른 것 같지만 데이터가 커지면 폭발. 책 3교시 5장 "기본값의 함정". |
| 측정 도구는 무엇을 썼나? | `show_sql=true` 콘솔 로그 + Hibernate `Statistics`로 호출 수 카운트. 응답 시간은 hey 부하로 3회 평균. |

## 출처

- 작업 상세: `03-week2-jpa/evidence/n-plus-one-before.md`, `n-plus-one-after.md`
- 변경 감지/주인 결정 설명: `03-week2-jpa/evidence/dirty-checking-snapshot.md`, `association-owner-decision.md`
