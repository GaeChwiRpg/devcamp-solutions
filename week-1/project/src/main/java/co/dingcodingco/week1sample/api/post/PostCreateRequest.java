package co.dingcodingco.week1sample.api.post;

import jakarta.validation.constraints.NotBlank;

public record PostCreateRequest(
    @NotBlank String title,
    @NotBlank String content,
    @NotBlank String author
) {
}
