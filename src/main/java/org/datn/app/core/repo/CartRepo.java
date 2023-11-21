package org.datn.app.core.repo;

import org.datn.app.core.entity.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepo extends JpaRepository<Cart,Long> {
    Page<Cart> findByUserId(Long userId, Pageable pageable);
}
