package co.dingcodingco.devcampstarter.service;

import co.dingcodingco.devcampstarter.domain.product.Product;
import co.dingcodingco.devcampstarter.domain.product.ProductRepository;
import co.dingcodingco.devcampstarter.domain.product.ProductStatus;
import java.util.List;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Week 7 캐시 미션 (한 가지 풀이): 최신 ACTIVE 목록은 같은 결과를 자주 반환하고,
    // 변경(상품 등록/상태 전환/재고 차감)은 그보다 드물어 캐시 적합 후보.
    // 키 = "latest-active". 만료/무효화는 stock 변경 시 evict 만으로 처리한다 (TTL 미사용 sample).
    // 학생은 다른 endpoint(searchByKeyword) 또는 다른 invalidation(TTL/이벤트)을 골라도 된다.
    @Cacheable(value = "latest-active-products", key = "'all'")
    @Transactional(readOnly = true)
    public List<Product> findLatestActiveProducts() {
        return productRepository.findTop20ByStatusOrderByCreatedAtDesc(ProductStatus.ACTIVE);
    }

    @Transactional(readOnly = true)
    public List<Product> searchByKeyword(String keyword) {
        return productRepository.searchByKeyword(ProductStatus.ACTIVE, keyword);
    }

    @CacheEvict(value = "latest-active-products", allEntries = true)
    @Transactional
    public void decreaseStock(Long productId, int quantity) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException(productId));
        product.decreaseStock(quantity);
    }
}
