package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.VoucherProductCategoryLink;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor

public class VoucherProductCategoryLinkServiceImpl implements VoucherProductCategoryLinkService{
    @Override
    public VoucherProductCategoryLink doInsert(VoucherProductCategoryLink voucherProductCategoryLink) {
        return null;
    }

    @Override
    public VoucherProductCategoryLink doUpdateById(VoucherProductCategoryLink voucherProductCategoryLink, Long aLong) {
        return null;
    }

    @Override
    public VoucherProductCategoryLink doDeleteById(Long aLong) {
        return null;
    }

    @Override
    public VoucherProductCategoryLink findById(Long aLong) {
        return null;
    }

    @Override
    public List<VoucherProductCategoryLink> findAll() {
        return null;
    }

    @Override
    public Page<VoucherProductCategoryLink> findAll(Integer page, Integer size) {
        return null;
    }
}
