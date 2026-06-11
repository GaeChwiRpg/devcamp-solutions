package co.dingcodingco.week1sample.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import co.dingcodingco.week1sample.domain.post.Post;
import co.dingcodingco.week1sample.repository.InMemoryPostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PostServiceTest {

    private InMemoryPostRepository postRepository;
    private PostService postService;

    @BeforeEach
    void setUp() {
        postRepository = new InMemoryPostRepository();
        postService = new PostService(postRepository);
    }

    @Test
    void create() {
        Post created = postService.create("Week 1 제출 예시", "본문", "dingcodingco");

        assertThat(created.id()).isEqualTo(1L);
        assertThat(created.title()).isEqualTo("Week 1 제출 예시");
    }

    @Test
    void findById() {
        Post created = postService.create("조회 테스트", "본문", "dingcodingco");

        Post found = postService.findById(created.id());

        assertThat(found.content()).isEqualTo("본문");
    }

    @Test
    void update() {
        Post created = postService.create("수정 전", "본문", "dingcodingco");

        Post updated = postService.update(created.id(), "수정 후", "변경된 본문");

        assertThat(updated.title()).isEqualTo("수정 후");
        assertThat(updated.content()).isEqualTo("변경된 본문");
    }

    @Test
    void throwsWhenPostMissing() {
        assertThatThrownBy(() -> postService.findById(999L))
            .isInstanceOf(PostNotFoundException.class);
    }
}
