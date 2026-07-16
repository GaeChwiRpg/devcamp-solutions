package co.dingcodingco.devcampstarter.domain.product;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findTop20ByStatusOrderByCreatedAtDesc(ProductStatus status);

    // Week 6 핫스팟 개선 (한 가지 풀이): Pageable로 결과 개수를 제한해서 LIKE 매칭의 풀스캔 비용을 줄인다.
    // 학생은 다른 후보(Lucene/검색엔진 분리, full-text index)를 골라도 된다.
    @Query("select p from Product p where p.status = :status and lower(p.title) like lower(concat('%', :keyword, '%')) order by p.createdAt desc")
    List<Product> searchByKeyword(@Param("status") ProductStatus status, @Param("keyword") String keyword, Pageable pageable);
}
