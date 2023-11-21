package org.datn.app.core.repo;

import org.datn.app.core.entity.VoucherAccountLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherAccountLinkRepo extends JpaRepository<VoucherAccountLink,Long> {
}
