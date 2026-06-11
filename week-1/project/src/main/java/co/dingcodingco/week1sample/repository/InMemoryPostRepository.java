package co.dingcodingco.week1sample.repository;

import co.dingcodingco.week1sample.domain.post.Post;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryPostRepository {

    private final AtomicLong sequence = new AtomicLong(1L);
    private final Map<Long, Post> posts = new ConcurrentHashMap<>();

    public Post save(String title, String content, String author) {
        long id = sequence.getAndIncrement();
        Post post = Post.create(id, title, content, author);
        posts.put(id, post);
        return post;
    }

    public Optional<Post> findById(Long postId) {
        return Optional.ofNullable(posts.get(postId));
    }

    public List<Post> findAll() {
        return posts.values().stream()
            .sorted(Comparator.comparing(Post::id).reversed())
            .toList();
    }

    public Post update(Post post) {
        posts.put(post.id(), post);
        return post;
    }

    public void clear() {
        posts.clear();
        sequence.set(1L);
    }
}
