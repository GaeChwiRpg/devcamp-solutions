package co.dingcodingco.week2sample.api.post;

import co.dingcodingco.week2sample.service.PostQueryService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
public class PostQueryController {

    private final PostQueryService postQueryService;

    public PostQueryController(PostQueryService postQueryService) {
        this.postQueryService = postQueryService;
    }

    @GetMapping("/published")
    public List<PostWithCommentsResponse> publishedPosts() {
        return postQueryService.findPublishedPosts().stream()
            .map(PostWithCommentsResponse::from)
            .toList();
    }
}
