# API Contract

- `POST /api/posts`
  - 201 Created
  - 제목과 본문이 비어 있지 않으면 새 게시글을 생성한다.
- `GET /api/posts`
  - 200 OK
  - 최신 게시글 목록을 반환한다.
- `GET /api/posts/{postId}`
  - 200 OK
  - 존재하지 않는 id면 404를 반환한다.
