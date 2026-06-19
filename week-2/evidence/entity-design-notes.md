# Entity Design Notes

- `Post`가 `Comment`를 소유하는 방향으로 두지 않고, `Comment.post`를 연관관계 주인으로 뒀다.
- 컬렉션은 조회 편의를 위해 `List<Comment>`를 두되, 실제 외래키 관리는 `Comment`가 담당한다.
- 예제에서는 연관관계 이해를 우선으로 두고 soft delete 같은 운영 이슈는 제외했다.
