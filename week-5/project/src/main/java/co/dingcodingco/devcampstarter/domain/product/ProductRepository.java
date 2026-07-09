package co.dingcodingco.devcampstarter.domain.product;

import jakarta.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findTop20ByStatusOrderByCreatedAtDesc(ProductStatus status);

    @Query("select p from Product p where p.status = :status and lower(p.title) like lower(concat('%', :keyword, '%')) order by p.createdAt desc")
    List<Product> searchByKeyword(@Param("status") ProductStatus status, @Param("keyword") String keyword);

    // Week 5 동시성 미션 (한 가지 풀이): pessimistic write lock
    // 학생은 optimistic / 분산락 등 다른 후보를 골라도 된다 (MEASUREMENT-OPTIONS.md 참고).
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.id = :id")
    Optional<Product> findByIdForUpdate(@Param("id") Long id);
}
