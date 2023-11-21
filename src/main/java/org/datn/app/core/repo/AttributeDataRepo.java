package org.datn.app.core.repo;

import org.datn.app.core.entity.AttributeData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttributeDataRepo extends JpaRepository<AttributeData,Long> {
    List<AttributeData> findByProduct_Id(Long id);
}
