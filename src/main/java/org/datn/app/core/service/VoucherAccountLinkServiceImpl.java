package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.VoucherAccountLink;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = RuntimeException.class)
public class VoucherAccountLinkServiceImpl implements VoucherAccountLinkService{
    @Override
    public VoucherAccountLink doInsert(VoucherAccountLink voucherAccountLink) {
        return null;
    }

    @Override
    public VoucherAccountLink doUpdateById(VoucherAccountLink voucherAccountLink, Long aLong) {
        return null;
    }

    @Override
    public VoucherAccountLink doDeleteById(Long aLong) {
        return null;
    }

    @Override
    public VoucherAccountLink findById(Long aLong) {
        return null;
    }

    @Override
    public List<VoucherAccountLink> findAll() {
        return null;
    }

    @Override
    public Page<VoucherAccountLink> findAll(Integer page, Integer size) {
        return null;
    }
}
