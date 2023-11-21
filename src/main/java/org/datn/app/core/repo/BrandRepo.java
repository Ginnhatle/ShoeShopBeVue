package org.datn.app.core.repo;

import org.datn.app.core.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepo extends JpaRepository<Brand,Long> {
    @Query("select b from Brand b where b.isTrash = false")
    Page<Brand> findAll(Pageable pageable);

    @Query("select b from Brand b where b.isTrash = false")
    List<Brand> findAll();

    @Query("select b from Brand b where b.isTrash = false and b.name = ?1")
    Brand findByName(String name);
}
