package org.datn.app.core.repo;

import org.datn.app.core.entity.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class OrderRepoTest {
    @Autowired
    private OrderRepo orderRepo;

    @Test
    void findByUserId() {
        Collection<Order> order = orderRepo.findByUserId(1L);
        assertNotNull(order);
    }
}