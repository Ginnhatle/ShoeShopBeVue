package org.datn.app.core.repo;

import org.datn.app.core.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> , JpaSpecificationExecutor<Order> {
    Order findByCode(String code);

    Order findByCodeAndStatus(String code, String status);

    List<Order> findByUserIdAndStatus(Long userId, Integer status);

    Page<Order> findAllByUserId(Long userId, Pageable pageable);

    @Query("select o from Order o where " +
            "(?1 IS NULL OR o.status = ?1 ) " +
            "AND (o.created IS NULL OR o.created >= ?2) " +
            "AND (o.created IS NULL OR o.created <= ?3)")
    Page<Order> findAllByStatus(String keyword ,Integer status, Date fromDate, Date toDate, Pageable pageable);

    @Query("select o from Order o where o.user.id = ?1 ORDER BY o.created DESC  ")
    List<Order> findByUserId(Long userId);
}
