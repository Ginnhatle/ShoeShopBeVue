package org.datn.app.core.service;

import org.datn.app.core.entity.Brand;
import org.datn.app.core.entity.extend.BrandResponse;
import org.datn.app.core.service.base.ServiceCore;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandService extends ServiceCore<Brand,Long> {
    @Query("select p from Brand p where p.name = ?1")
    Brand findByName(String name);

    List<BrandResponse> findAllData();
}
