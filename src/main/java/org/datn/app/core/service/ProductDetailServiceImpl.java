package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.ProductDetail;
import org.datn.app.core.repo.ProductDetailRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Transactional(rollbackOn = RuntimeException.class)
@Service
@RequiredArgsConstructor
public class ProductDetailServiceImpl implements ProductDetailService{
    private final ProductDetailRepo productDetailRepo;
    @Override
    public ProductDetail doInsert(ProductDetail productDetail) {
        return productDetailRepo.save(productDetail);
    }

    @Override
    public ProductDetail doUpdateById(ProductDetail productDetail, Long aLong) {
        ProductDetail productDetail1 = productDetailRepo.findById(aLong).get();
        productDetail1.setQuantity(productDetail.getQuantity());
        // productDetail1.setSize(productDetail.getSize());
        return productDetailRepo.save(productDetail1);
    }

    @Override
    public ProductDetail doDeleteById(Long aLong) {
        ProductDetail productDetail = productDetailRepo.findById(aLong).orElse(null);
        productDetailRepo.delete(productDetail);
        return null;
    }

    @Override
    public ProductDetail findById(Long aLong) {
        return productDetailRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<ProductDetail> findAll() {
        return productDetailRepo.findAll();
    }

    @Override
    public Page<ProductDetail> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return productDetailRepo.findAll(pageable);
    }
}
