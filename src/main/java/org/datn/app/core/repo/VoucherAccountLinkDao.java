package org.datn.app.core.repo;

import org.datn.app.core.entity.Voucher;
import org.datn.app.core.entity.VoucherAccountLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherAccountLinkDao extends JpaRepository<VoucherAccountLink,Long> {
    @Query("select v from VoucherAccountLink v where v.user.id = ?1 and v.isUsed = ?2" +
            " and v.voucher.isAutoApply = true")
    List<VoucherAccountLink> findAllByUserIdAndIsUsed(Long userId,Boolean isUsed);

    List<VoucherAccountLink> findAllByVoucherId(Long voucherId);

    List<VoucherAccountLink> findAllByUserId(Long userId);
}
