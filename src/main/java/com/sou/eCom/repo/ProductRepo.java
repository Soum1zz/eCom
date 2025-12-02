package com.sou.eCom.repo;

import com.sou.eCom.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
    public Optional<Product> findByProductId(Long productId);
    @Query("SELECT p FROM Product p WHERE "+
            "LOWER(p.productName) LIKE LOWER (CONCAT('%',:key,'%')) OR "+
            "LOWER(p.productDescription) LIKE LOWER (CONCAT('%',:key,'%')) OR "+
            "LOWER(p.productBrand) LIKE LOWER (CONCAT('%',:key,'%')) OR "+
            "LOWER(p.productCategory) LIKE LOWER (CONCAT('%',:key,'%'))")
    List<Product> search(String key);
}
