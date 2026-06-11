package co.dingcodingco.week1sample.api.post;

import co.dingcodingco.week1sample.domain.post.Post;

public record PostResponse(
    Long id,
    String title,
    String content,
    String author
) {
    public static PostResponse from(Post post) {
        return new PostResponse(post.id(), post.title(), post.content(), post.author());
    }
}
