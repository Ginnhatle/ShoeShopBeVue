package org.datn.app.core.repo;

import org.datn.app.core.entity.Voucher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoucherRepo extends JpaRepository<Voucher,Long> {

    @Query("select v from Voucher v where v.isTrash = false")
    Page<Voucher> findAll(Pageable pageable);

    Optional<Voucher> findByCode(String code);

}
