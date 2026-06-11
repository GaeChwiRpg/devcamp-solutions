package co.dingcodingco.week1sample.service;

import co.dingcodingco.week1sample.domain.post.Post;
import co.dingcodingco.week1sample.repository.InMemoryPostRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final InMemoryPostRepository postRepository;

    public PostService(InMemoryPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post create(String title, String content, String author) {
        return postRepository.save(title, content, author);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
            .orElseThrow(() -> new PostNotFoundException(postId));
    }

    public Post update(Long postId, String title, String content) {
        Post post = findById(postId);
        return postRepository.update(post.update(title, content));
    }
}
