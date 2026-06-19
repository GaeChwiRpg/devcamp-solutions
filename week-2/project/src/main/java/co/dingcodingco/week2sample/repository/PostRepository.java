package co.dingcodingco.week2sample.repository;

import co.dingcodingco.week2sample.domain.post.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("""
        select distinct p
        from Post p
        left join fetch p.comments
        where p.published = true
        order by p.id desc
        """)
    List<Post> findPublishedPostsWithComments();
}
