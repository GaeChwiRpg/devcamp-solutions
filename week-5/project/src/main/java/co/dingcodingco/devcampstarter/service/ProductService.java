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
        // Week 5 동시성 미션 적용: pessimistic write lock으로 같은 row 동시 차감을 직렬화.
        // 락 전 코드는 findById였고, 동시 요청 시 음수 재고가 발생했다.
        // (다른 후보: Product에 @Version 추가 후 optimistic lock + retry, 또는 DB 분산락)
        Product product = productRepository.findByIdForUpdate(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
        product.decreaseStock(quantity);
    }
}
