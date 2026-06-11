package co.dingcodingco.week1sample.service;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(Long postId) {
        super("post not found");
    }
}
