package co.dingcodingco.week2sample.repository;

import static org.assertj.core.api.Assertions.assertThat;

import co.dingcodingco.week2sample.domain.comment.Comment;
import co.dingcodingco.week2sample.domain.post.Post;
import java.util.List;
import org.junit.jupiter.api.Test;

class PostRepositoryQueryShapeTest {

    @Test
    void fetchJoinSampleShape() {
        Post post = new Post("JPA 전환 예시", "dingcodingco", true);
        post.addComment(new Comment("첫 댓글"));
        post.addComment(new Comment("두 번째 댓글"));

        assertThat(post.getComments()).hasSize(2);
        assertThat(post.isPublished()).isTrue();
    }
}
