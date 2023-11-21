package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.constant.ProductConstant;
import org.datn.app.core.entity.Brand;
import org.datn.app.core.entity.extend.BrandResponse;
import org.datn.app.core.repo.BrandRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepo brandRepo;
    @Override
    public Brand doInsert(Brand brand) {
        return brandRepo.save(brand);
    }

    @Override
    public Brand doUpdateById(Brand brand, Long aLong) {
        Brand brand1 = brandRepo.findById(aLong).get();
        brand1.setName(brand.getName());
        brand1.setWebsite(brand.getWebsite());
        return brandRepo.save(brand1);
    }

    @Override
    public Brand doDeleteById(Long aLong) {
        Brand brand = brandRepo.findById(aLong).get();
        brand.setIsTrash(true);
        return brandRepo.save(brand);
    }

    @Override
    public Brand findById(Long aLong) {
        return brandRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Brand> findAll() {
        return brandRepo.findAll();
    }

    @Override
    public Page<Brand> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return brandRepo.findAll(pageable);
    }

    @Override
    public Brand findByName(String name) {
        Brand brand = brandRepo.findByName(name);
        if(brand == null){
            return null;
        }
        return brand;
    }

    @Override
    public List<BrandResponse> findAllData() {
        List<BrandResponse> brandResponseList = new ArrayList<>();
        List<Brand> brandList = brandRepo.findAll();
        for (Brand brand : brandList) {
            BrandResponse brandResponse = new BrandResponse();
            brandResponse.setId(brand.getId());
            brandResponse.setName(brand.getName());
            brandResponse.setWebsite(brand.getWebsite());
            brandResponse.setTotalProduct(brand.getProducts().stream().filter(product -> product.getStatus() == ProductConstant.EFFECT).count());
            brandResponseList.add(brandResponse);
        }
        return brandResponseList;
    }
}
