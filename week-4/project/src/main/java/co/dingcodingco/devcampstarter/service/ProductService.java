package co.dingcodingco.devcampstarter.service;

import co.dingcodingco.devcampstarter.domain.product.Product;
import co.dingcodingco.devcampstarter.domain.product.ProductRepository;
import co.dingcodingco.devcampstarter.domain.product.ProductStatus;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> findLatestActiveProducts() {
        return productRepository.findTop20ByStatusOrderByCreatedAtDesc(ProductStatus.ACTIVE);
    }

    @Transactional(readOnly = true)
    public List<Product> searchByKeyword(String keyword) {
        return productRepository.searchByKeyword(ProductStatus.ACTIVE, keyword);
    }

    @Transactional
    public void decreaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
        product.decreaseStock(quantity);
    }
}
