# 연관관계 주인 결정 — Post ↔ Comment

> 교재 3교시 2·3장: "외래 키를 가진 쪽이 주인" + 양방향 편의 메서드.

## 도메인

- Post (게시글) — 1
- Comment (댓글) — 다
- 관계: 1:N (한 게시글에 여러 댓글)

## 결정: Comment가 주인

### 왜?

DB 테이블에서 외래 키(`post_id`)는 **comments 테이블에 들어간다**. JPA의 원칙: 외래 키를 가진 쪽이 연관관계의 주인.

```
posts                  comments
+----+-------+         +----+---------+--------+
| id | title |         | id | post_id | body   |
+----+-------+         +----+---------+--------+
                            ^^^^^^^^
                            FK 컬럼은 여기
```

### 책의 비교 시뮬레이션 (3교시 3장)

#### 만약 Post가 주인이라면?

```java
@Entity
class Post {
    @OneToMany
    @JoinColumn(name = "post_id")  // 다른 테이블 컬럼을 내가 책임진다? 어색
    private List<Comment> comments;
}
```

문제: Post 엔티티의 변경(`comments.add()`)이 다른 테이블(comments)의 외래 키 UPDATE 쿼리를 발생시킨다. 의도와 SQL이 어긋남.

#### Comment가 주인 (선택)

```java
@Entity
class Comment {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;  // 외래 키를 내가 책임진다
}

@Entity
class Post {
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments;  // 거울 (mappedBy)
}
```

→ 외래 키 SQL은 Comment 측 변경에서 발생. 의도와 SQL 일치.

## 양방향 편의 메서드 (책 3교시 3장 13절)

양쪽 객체 그래프를 항상 일관된 상태로 유지하기 위한 보호 장치:

```java
@Entity
class Post {
    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    // 편의 메서드
    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);   // ← 양쪽을 같이 채운다
    }
}
```

이 패턴이 없으면 한쪽만 채워서 영속성 컨텍스트 안 1차 캐시에는 불일치, DB는 정상 같은 이상한 상태가 발생.

## LAZY 기본

- `@ManyToOne`: 책 5장 기본은 EAGER → **명시적으로 LAZY로 변경** (책 5장의 강한 권고)
- `@OneToMany`: 기본이 LAZY → 그대로 유지

이번 미션의 모든 연관관계는 LAZY. N+1 발생 시점을 의식적으로 보고 fetch join/@EntityGraph로 해결.

## 학습

- 외래 키 위치 → 주인 결정 → 양방향 편의 메서드 → LAZY 기본 — **이 4 단계가 항상 같이 간다**.
- 한 단계라도 어기면 영속성 컨텍스트 동기화가 깨지거나 N+1 폭증.
- Week 9 팀 프로젝트에서 더 복잡한 도메인이 들어올 때 다시 이 4 단계로 돌아오는 게 안전.
