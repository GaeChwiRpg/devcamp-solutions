package co.dingcodingco.devcampstarter.domain.product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findTop20ByStatusOrderByCreatedAtDesc(ProductStatus status);

    @Query("select p from Product p where p.status = :status and lower(p.title) like lower(concat('%', :keyword, '%')) order by p.createdAt desc")
    List<Product> searchByKeyword(@Param("status") ProductStatus status, @Param("keyword") String keyword);
}
