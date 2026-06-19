---
mission_id: "03-week2-jpa"
week: 2
submission_type: "code"
status: "sample"
---

# Week 2 JPA — 영속성 컨텍스트 + 연관관계 주인 + LAZY/N+1

## 시도

- Week 1 게시판 코드를 JPA 기반으로 리팩토링
- Post + Comment 연관관계 추가 (`@ManyToOne` + `@OneToMany`)
- 모든 연관관계 LAZY 기본 (책 3교시 5장)
- N+1 발생 → fetch join / @EntityGraph 적용 후 쿼리 차이 비교
- 변경 감지(Dirty Checking)로 update 메서드 1개 작성 (save() 호출 X)

## 판단

- 연관관계 주인: Comment(외래 키 보유) → 책 3교시 2장 "외래 키를 가진 쪽이 주인" 원칙
- ID 전략: MySQL/H2 기반이라 `IDENTITY` (책 2교시 6절 "왜 IDENTITY가 대세인가" 적용)
- LAZY 기본: `@OneToMany` 기본이 LAZY지만 `@ManyToOne`은 EAGER가 기본 → 명시적으로 LAZY 지정해서 N+1을 의식적으로 다룸
- fetch join 선택: 단발 조회에서 게시글 + 댓글을 같이 가져올 때 사용. 페이징 시에는 EntityGraph + Pageable 조합 검토

## 결과

- LAZY로 두고 게시글 목록 조회 시 N+1 재현 (게시글 N개 → 댓글 select N번 추가)
- `@EntityGraph(attributePaths="comments")` 적용 후 쿼리 1개로 통합 (`evidence/n-plus-one-after.md`)
- 변경 감지: `Post.update(title, content)` 메서드만 호출 → 트랜잭션 종료 시 자동 UPDATE SQL 발행
- save() 호출 없는 update가 동작하는 것을 콘솔 SQL 로그로 확인

## 회고

- 영속성 컨텍스트 4 기능 중 **변경 감지**가 가장 충격적. "update 메서드를 찾지 마세요"라는 책의 메시지를 손으로 검증.
- N+1은 LAZY를 두면 항상 보이는 신호 — `show_sql` 켜는 습관이 다음 주차 인덱스/프로파일링까지 이어짐.
- 연관관계 주인 결정은 한 번 잘못하면 양방향에서 데이터 안 박힘 — 책 3교시 3장 편의 메서드 패턴 정립.

## 제출 파일

- `evidence/entity-design-notes.md`
- `evidence/association-owner-decision.md`
- `evidence/n-plus-one-before.md`
- `evidence/n-plus-one-after.md`
- `evidence/dirty-checking-snapshot.md`
- `evidence/n1-detection-guide.md` (학습 보조)
- `project/src/main/java/...`
