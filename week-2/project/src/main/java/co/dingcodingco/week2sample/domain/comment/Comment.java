package co.dingcodingco.week2sample.domain.comment;

import co.dingcodingco.week2sample.domain.post.Post;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "post_id")
    @ManyToOne(optional = false)
    private Post post;

    private String body;

    protected Comment() {
    }

    public Comment(String body) {
        this.body = body;
    }

    public void assignPost(Post post) {
        this.post = post;
    }

    public String getBody() {
        return body;
    }
}
