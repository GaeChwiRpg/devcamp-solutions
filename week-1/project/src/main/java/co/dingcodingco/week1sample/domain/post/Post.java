package co.dingcodingco.week1sample.domain.post;

import java.time.LocalDateTime;

public record Post(
    Long id,
    String title,
    String content,
    String author,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    public static Post create(Long id, String title, String content, String author) {
        LocalDateTime now = LocalDateTime.now();
        return new Post(id, title, content, author, now, now);
    }

    public Post update(String title, String content) {
        return new Post(id, title, content, author, createdAt, LocalDateTime.now());
    }
}
