# N+1 After

- `findPublishedPostsWithComments()`에서 `left join fetch`를 적용했다.
- 게시글/댓글을 한 번에 가져오고, 응답 조립은 서비스 레이어에서 처리했다.
- SQL 로그 기준으로 핵심 조회가 1번으로 줄어든다는 점을 제출 근거로 남겼다.
