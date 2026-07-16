package co.dingcodingco.devcampstarter.service;

import co.dingcodingco.devcampstarter.domain.product.Product;
import co.dingcodingco.devcampstarter.domain.product.ProductRepository;
import co.dingcodingco.devcampstarter.domain.product.ProductStatus;
import java.util.List;
import org.springframework.data.domain.PageRequest;
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

    // Week 6 개선: 검색 결과를 50건으로 제한해 LIKE 매칭의 풀스캔 비용을 줄인다.
    // 핫스팟 1순위로 잡혔던 메서드 (자세한 우선순위는 evidence/bottlenecks.md).
    private static final int SEARCH_LIMIT = 50;

    @Transactional(readOnly = true)
    public List<Product> searchByKeyword(String keyword) {
        return productRepository.searchByKeyword(ProductStatus.ACTIVE, keyword, PageRequest.of(0, SEARCH_LIMIT));
    }

    @Transactional
    public void decreaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
        product.decreaseStock(quantity);
    }
}
