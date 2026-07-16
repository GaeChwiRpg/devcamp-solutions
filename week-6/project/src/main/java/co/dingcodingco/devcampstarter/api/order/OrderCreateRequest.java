package co.dingcodingco.devcampstarter.api.order;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record OrderCreateRequest(
    @NotNull Long productId,
    @Min(1) int quantity
) {
}
