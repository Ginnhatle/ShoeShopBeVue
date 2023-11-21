package org.datn.app.core.service;

import org.datn.app.core.dto.OrderRequest;
import org.datn.app.core.dto.OrderStatisticalDto;
import org.datn.app.core.entity.Order;
import org.datn.app.core.service.base.ServiceCore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface OrderService extends ServiceCore<Order, Long> {
    Order findByCode(String code);

    Order findByCodeAndStatus(String code, String status);

    Map<String, Object> doOrder(OrderRequest model);

    ResponseEntity<?> approveOrder(Long id);

    ResponseEntity<?> cancelOrder(Long id);

    // hoàn hàng
    ResponseEntity<?> returnOrder(Long id,String note);

    Page<Order> findAllByUserId(Long userId, Pageable pageable);

    Page<Order> findAll(Integer page, Integer size);

    // thống kê
    Page<Order> findAllByStatus(OrderStatisticalDto model);

    // giao hàng
    ResponseEntity<?> deliveryOrder(Long id);

    // nhận hàng
    ResponseEntity<?> receiveOrder(Long id);

    // xem chi tiết đơn hàng
    ResponseEntity<?> detailOrder(Long id);

    Order findFirstByUserId(Long userId);
}
