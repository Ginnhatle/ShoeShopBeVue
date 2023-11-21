package org.datn.app.core.repo;

import org.datn.app.core.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepo extends JpaRepository<Category,Long> {

    @Query("select c from Category c where c.isTrash = false")
    Page<Category> findAll(Pageable pageable);

    @Query("select c from Category c where c.isTrash = false and c.name = ?1")
    Category findByName(String name);

    @Query("select c from Category c where c.isTrash = false")
    List<Category> findAll();

    List<Category> findAllByIsTrash(Boolean isTrash);
}
