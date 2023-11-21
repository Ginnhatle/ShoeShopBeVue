package org.datn.app.core.service;

import org.datn.app.core.entity.Category;
import org.datn.app.core.entity.Product;
import org.datn.app.core.entity.User;
import org.datn.app.core.entity.Voucher;
import org.datn.app.core.entity.extend.VoucherResponse;
import org.datn.app.core.service.base.ServiceCore;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface VoucherService extends ServiceCore<Voucher, Long> {
    Page<VoucherResponse> findAllVoucher(Integer page, Integer size);

    ResponseEntity<?> addVoucher(Voucher voucher);

    Voucher findVoucherByID(Long id);

    void deleteVoucher(Long id);

    ResponseEntity<?> updateVoucher(Voucher voucher);

    Voucher findByCode(String code);

    List<Product> findProductByVoucherId(Long id);

    List<Category> findCategoryByVoucherId(Long id);

    Voucher addVoucherToProduct(Long voucherId, List<Long> productId);

    Voucher addVoucherToCategory(Long voucherId, List<Long> categoryId);

    void deleteVoucherProduct(Long voucherId, Long productId);

    void deleteVoucherCategory(Long voucherId, Long categoryId);

    List<User> findAllByVoucherId(Long userId);

    Voucher addVoucherToUser(Long voucherId, List<Long> userId);

    List<Voucher> findAllByUserId(Long userId);
}
