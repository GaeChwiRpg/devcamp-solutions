package co.dingcodingco.week2sample.api.post;

import co.dingcodingco.week2sample.domain.post.Post;
import java.util.List;

public record PostWithCommentsResponse(
    Long id,
    String title,
    String author,
    int commentCount,
    List<String> comments
) {
    public static PostWithCommentsResponse from(Post post) {
        return new PostWithCommentsResponse(
            post.getId(),
            post.getTitle(),
            post.getAuthor(),
            post.getComments().size(),
            post.getComments().stream().map(comment -> comment.getBody()).toList()
        );
    }
}
