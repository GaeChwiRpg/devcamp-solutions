package co.dingcodingco.week2sample.service;

import co.dingcodingco.week2sample.domain.post.Post;
import co.dingcodingco.week2sample.repository.PostRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostQueryService {

    private final PostRepository postRepository;

    public PostQueryService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional(readOnly = true)
    public List<Post> findPublishedPosts() {
        return postRepository.findPublishedPostsWithComments();
    }
}
