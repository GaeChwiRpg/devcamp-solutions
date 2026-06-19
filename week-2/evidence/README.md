# Evidence

이 폴더는 교재 `books/03-week2-lv2-jpa.md`의 핵심(영속성 컨텍스트 + 연관관계 주인 + LAZY/N+1 + 변경 감지)이 본인 작업에 적용된 흔적을 남기는 곳입니다.

## 엔티티 / 연관관계 자산

- `entity-design-notes.md` — Post/Comment 엔티티 설계 + @Id 전략 결정 (책 2교시 1·2장)
- `association-owner-decision.md` — 연관관계 주인 결정 근거 (책 3교시 2·3장)

## 영속성 컨텍스트 4 기능 자산

- `n-plus-one-before.md` — Hibernate `show_sql` 출력 (LAZY 미적용 또는 N+1 발생 시점)
- `n-plus-one-after.md` — fetch join / `@EntityGraph` 적용 후 쿼리 (책 3교시 6장)
- `dirty-checking-snapshot.md` — 변경 감지 활용 사례 (책 1교시 7장)

## 학습 보조

- `n1-detection-guide.md` — N+1을 어떻게 켜서 보는지 단계 (답안 X)

## 작성 원칙

- "JPA로 바꿨다"는 사실보다 "**왜 이 매핑/주인/LAZY 결정을 했는지**"가 본문의 80% 이상을 차지해야 합니다.
- 변경 감지 evidence는 save() **호출이 없는** update 메서드 1개 이상 보여주기.
- 연관관계 주인은 양방향이라면 반드시 결정 근거 1단락.
