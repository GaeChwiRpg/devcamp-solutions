package co.dingcodingco.week1sample.api.post;

import co.dingcodingco.week1sample.service.PostService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse create(@Valid @RequestBody PostCreateRequest request) {
        return PostResponse.from(postService.create(request.title(), request.content(), request.author()));
    }

    @GetMapping
    public List<PostResponse> findAll() {
        return postService.findAll().stream()
            .map(PostResponse::from)
            .toList();
    }

    @GetMapping("/{postId}")
    public PostResponse findById(@PathVariable Long postId) {
        return PostResponse.from(postService.findById(postId));
    }

    @PutMapping("/{postId}")
    public PostResponse update(@PathVariable Long postId, @Valid @RequestBody PostUpdateRequest request) {
        return PostResponse.from(postService.update(postId, request.title(), request.content()));
    }
}
