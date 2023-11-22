package org.datn.app.core.repo;

import org.datn.app.core.entity.AttributeData;
import org.datn.app.core.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeDataRepo extends JpaRepository<AttributeData,Long> {
    @Modifying
    void deleteAllByProductId(Long id);
    List<AttributeData> findByProduct(Product product);
}
