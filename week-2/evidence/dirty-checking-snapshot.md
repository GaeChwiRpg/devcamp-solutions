# Dirty Checking (변경 감지) 활용 사례

> 교재 1교시 7장: "update 메서드를 찾지 마세요" + 영속성 컨텍스트의 변경 감지.

## 시나리오

게시글 수정 API: 제목과 본문을 수정. **save()/update() 호출 없이** 변경 감지로 처리.

## 코드

### Post 도메인

```java
@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 80)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // ... 생성자, getter ...

    /**
     * 도메인 메서드. 영속 상태일 때 호출되면 트랜잭션 종료 시 자동 UPDATE.
     * Repository.save() 호출이 필요 없다.
     */
    public void update(String title, String content) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title is required");
        }
        this.title = title;
        this.content = content;
    }
}
```

### Service

```java
@Service
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public void update(Long id, String title, String content) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException(id));
        post.update(title, content);
        // ← save() 호출 없음. 트랜잭션 종료 시 변경 감지로 자동 UPDATE.
    }
}
```

## 동작 검증 — 콘솔 SQL 로그

`application.yml`에 `show_sql: true` 켠 상태에서 `PUT /api/posts/1` 호출:

```
Hibernate: select p1_0.id, p1_0.title, p1_0.content, p1_0.created_at from posts p1_0 where p1_0.id=?
Hibernate: update posts set title=?, content=? where id=?
```

→ select 1번 + update 1번. **save() 호출 없이** UPDATE가 발행된다.

## 책의 메시지 (3교시 7장 24·25절)

> "Hibernate는 영속 객체가 가진 1차 캐시 안 스냅샷과 트랜잭션 종료 시점의 객체 상태를 비교해서 변경된 컬럼만 UPDATE 쿼리에 포함한다."

→ JPA에서 update 메서드를 찾지 말 것. **도메인 객체의 메서드를 호출**하면 끝.

## 함정 (책 3교시 7장 25절)

- **준영속(detached) 상태**에서는 변경 감지 안 됨. `merge()` 또는 `save()` 필요.
- 트랜잭션 밖에서 객체를 수정하면 영속 상태가 아니라 안 잡힘.
- 그래서 Service의 update 메서드에 `@Transactional`이 필수 — 영속 상태를 트랜잭션 종료까지 유지.

## 학습

- 변경 감지는 **JPA의 가장 중요한 4 기능 중 하나**. 책 1교시 4 기능(1차 캐시 / 동일성 / 변경 감지 / 쓰기 지연) 중 가장 직접적으로 코드 양을 줄임.
- save() 호출이 없는 update 메서드 1개를 작성하고 SQL 로그로 검증한 것이 **이번 주 미션의 핵심 학습**.
- Week 9 팀 프로젝트에서 같은 패턴을 모든 도메인 메서드에 적용 예정.
