package co.dingcodingco.devcampstarter.api.product;

import co.dingcodingco.devcampstarter.service.ProductService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/products")
    public List<ProductResponse> latestProducts() {
        return productService.findLatestActiveProducts().stream()
            .map(ProductResponse::from)
            .toList();
    }

    @GetMapping("/api/products/search")
    public List<ProductResponse> search(@RequestParam String keyword) {
        return productService.searchByKeyword(keyword).stream()
            .map(ProductResponse::from)
            .toList();
    }

    @PostMapping("/api/products/{productId}/decrease")
    public void decreaseStock(@PathVariable Long productId, @RequestParam int quantity) {
        productService.decreaseStock(productId, quantity);
    }
}
