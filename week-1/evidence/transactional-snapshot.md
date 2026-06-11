# @Transactional Snapshot — 적용 위치와 근거

> 교재 5교시 3·4·5장: "다 주거나 아예 말거나 (원자성)" + AOP 작동 원리.

## 적용 표

| 메서드 | @Transactional | 근거 |
| --- | --- | --- |
| `PostController.create` | ❌ | Controller에는 절대 안 붙임 (책 5교시 5장: 트랜잭션은 Service 경계) |
| `PostService.create` | ✅ | 게시글 저장 + (선택) 작성자 카운터 증가 묶음 → 원자성 필요 |
| `PostService.findAll` | ❌ | 단일 read. 트랜잭션 cost > benefit |
| `PostService.findById` | ❌ | 단일 read. 같은 이유 |
| `PostService.update` | ✅ | findById + setter + flush 묶음 → 중간 실패 시 일관성 위험 |
| `PostRepository.*` | ❌ | Repository는 트랜잭션 경계의 안쪽 — 본인이 트랜잭션을 만들지 않음 |

## 책의 핵심 원칙 적용

### 원칙 1: "트랜잭션은 Service 경계"

Controller에는 안 붙임. AOP 프록시가 작동하려면 외부에서 호출되는 public 메서드에서 시작되어야 하는데, 그 시작점은 Service.

### 원칙 2: "single-read는 안 붙인다"

@Transactional은 트랜잭션 시작 + 커밋 + AOP 프록시 비용이 있음. 단일 SELECT 한 번에 그 비용을 지불할 가치 없음.

### 원칙 3: "묶음 작업에만 붙인다"

`PostService.create`가 만약 단순히 `repository.save(post)` 한 줄이라면 안 붙여도 됨. 하지만 "저장 + 카운터 증가" 같은 묶음이 들어가면 반드시 붙임 — 카운터 증가가 실패했을 때 게시글도 같이 롤백해야 일관성 유지.

## 안티패턴 회피

### 흔한 안티패턴: "Service 모든 메서드에 @Transactional"

- 비용 증가 (single-read에도 트랜잭션 시작/커밋)
- 의미가 흐려짐 ("이 어노테이션이 정말 필요해서 붙은 건지 모름")
- 나중에 트랜잭션 전파 옵션을 손볼 때 어디부터 봐야 할지 모름

### 흔한 안티패턴: "Controller에 @Transactional"

- Spring AOP 프록시가 가짜 객체로 끼어들기 때문에 **자기 자신 호출**(self-invocation)에서는 적용 안 됨.
- Controller에서 같은 클래스 안 다른 메서드를 호출할 때 트랜잭션이 안 걸림 → 학생이 "왜 안 되지" 디버깅 시간 폭증.
- 책 5교시 5장에 그 이유가 자세히 나와 있음.

## 검증

`evidence/test-results.md`의 테스트 중 1개는 의도적으로 트랜잭션 롤백을 검증:

```java
@Test
void shouldRollbackWhenCounterIncrementFails() {
    // given: counter 증가가 실패하도록 mock
    // when: postService.create(...) 호출
    // then: post가 DB에 안 남아야 한다 (롤백 확인)
}
```

## 학습

@Transactional은 **"붙이지 않는 결정"이 더 어렵다**. 안티패턴을 피해 의식적으로 위치를 결정한 흔적이 evidence의 핵심. 다음 주 JPA에서 영속성 컨텍스트를 배울 때 이 결정이 다시 의미를 가짐 (트랜잭션이 영속성 경계를 정함).
