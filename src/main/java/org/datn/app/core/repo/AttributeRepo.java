package org.datn.app.core.repo;

import org.datn.app.core.entity.Attribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttributeRepo extends JpaRepository<Attribute, Long> {
    Optional<Attribute> findByName(String name);

    @Query("select a from Attribute a where a.isTrash = false")
    Page<Attribute> findAll(Pageable pageable);
}
