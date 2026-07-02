package co.dingcodingco.devcampstarter.api.product;

import co.dingcodingco.devcampstarter.domain.product.Product;

public record ProductResponse(
    Long id,
    String title,
    String sku,
    String status,
    int stock,
    int price
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
            product.getId(),
            product.getTitle(),
            product.getSku(),
            product.getStatus().name(),
            product.getStock(),
            product.getPrice()
        );
    }
}
