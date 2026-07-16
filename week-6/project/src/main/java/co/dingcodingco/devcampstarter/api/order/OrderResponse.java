package co.dingcodingco.devcampstarter.api.order;

import co.dingcodingco.devcampstarter.domain.order.Order;

public record OrderResponse(
    Long id,
    Long productId,
    int quantity,
    String status
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
            order.getId(),
            order.getProductId(),
            order.getQuantity(),
            order.getStatus().name()
        );
    }
}
