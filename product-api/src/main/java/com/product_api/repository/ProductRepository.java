package com.product_api.repository;

import com.product_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    @Query("""
    SELECT p
    FROM Product p
    WHERE (:category IS NULL OR p.category = :category)
      AND (
            p.createdAt < :cursor
            OR (
                p.createdAt = :cursor
                AND p.id < :cursorId
            )
          )
    ORDER BY p.createdAt DESC, p.id DESC
""")
    List<Product> findProducts(
            @Param("category") String category,
            @Param("cursor") LocalDateTime cursor,
            @Param("cursorId") Long cursorId,
            Pageable pageable
    );
}
