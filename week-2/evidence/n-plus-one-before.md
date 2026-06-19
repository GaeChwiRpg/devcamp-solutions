# N+1 Before

- 게시글 10개를 조회한 뒤 각 게시글의 댓글 수를 화면에서 순회하며 읽었다.
- 로그 기준으로 `select post ...` 1번 이후 `select comment ... where post_id = ?`가 반복됐다.
- 게시글 수가 늘수록 쿼리 수가 그대로 늘어나는 문제가 있었다.
