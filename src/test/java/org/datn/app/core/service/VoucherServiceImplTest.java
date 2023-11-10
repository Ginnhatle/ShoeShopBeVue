package org.datn.app.core.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class VoucherServiceImplTest {
    @Autowired
    private VoucherService voucherService;

    @Test
    void findProductByVoucherId() {
        assertNotNull(voucherService.findProductByVoucherId(1L));
    }
}