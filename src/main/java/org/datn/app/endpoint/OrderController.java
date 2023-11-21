package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.dto.OrderRequest;
import org.datn.app.core.dto.OrderStatisticalDto;
import org.datn.app.core.entity.Order;
import org.datn.app.core.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/doOrder")
    public ResponseEntity<?> doOrder(@RequestBody OrderRequest model) {
        return ResponseEntity.ok(orderService.doOrder(model));
    }

    @PostMapping("/approve/{id}")
    public ResponseEntity<?> approveOrder(@PathVariable Long id) {
        return orderService.approveOrder(id);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<?> cancelOrder(@PathVariable Long id) {
        return orderService.cancelOrder(id);
    }

    @GetMapping("/findAll/{page}/{size}")
    public Page<Order> findAll(@PathVariable Integer page, @PathVariable Integer size) {
        return orderService.findAll(page, size);
    }

    @GetMapping("/findAllByUserId/{userId}/{page}/{size}")
    public Page<Order> findAllByUserId(@PathVariable Long userId, @PathVariable Integer page, @PathVariable Integer size) {
        Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        return orderService.findAllByUserId(userId, pageable);
    }

    @PostMapping("/return/{id}/{note}")
    public ResponseEntity<?> returnOrder(@PathVariable Long id, @PathVariable String note) {
        return orderService.returnOrder(id,note);
    }

    @PostMapping("/findAllByStatus")
    public Page<Order> findAllByStatus(@RequestBody OrderStatisticalDto model) {
        return orderService.findAllByStatus(model);
    }

    @PostMapping("/delivery/{id}")
    public ResponseEntity<?> deliveryOrder(@PathVariable Long id) {
        return orderService.deliveryOrder(id);
    }

    @PostMapping("/receive/{id}")
    public ResponseEntity<?> receiveOrder(@PathVariable Long id) {
        return orderService.receiveOrder(id);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> detailOrder(@PathVariable Long id) {
        return orderService.detailOrder(id);
    }

    @PostMapping("/findFirstByUserId/{userId}")
    public Order findFirstByUserId(@PathVariable Long userId) {
        return orderService.findFirstByUserId(userId);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", ex.getMessage());
        data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

}
