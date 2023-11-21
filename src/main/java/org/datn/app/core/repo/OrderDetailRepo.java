package org.datn.app.core.repo;

import org.datn.app.core.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepo extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findAllByOrderId(Long orderId);
}
