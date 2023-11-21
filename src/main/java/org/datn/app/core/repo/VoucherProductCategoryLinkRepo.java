package org.datn.app.core.repo;

import org.datn.app.core.entity.VoucherProductCategoryLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherProductCategoryLinkRepo extends JpaRepository<VoucherProductCategoryLink, Long> {
    List<VoucherProductCategoryLink> findAllByVoucherIdIn(List<Long> voucherIdList);

    VoucherProductCategoryLink findByVoucherIdAndProductId(Long voucherId, Long productId);

    VoucherProductCategoryLink findByVoucherIdAndCategoryId(Long voucherId, Long categoryId);
}
