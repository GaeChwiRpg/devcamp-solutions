package co.dingcodingco.week1sample.api.post;

import jakarta.validation.constraints.NotBlank;

public record PostUpdateRequest(
    @NotBlank String title,
    @NotBlank String content
) {
}
