package org.datn.app.core.service;

import org.datn.app.core.entity.Category;
import org.datn.app.core.entity.extend.CategoryResponse;
import org.datn.app.core.service.base.ServiceCore;

import java.util.List;
import java.util.Map;

public interface CategoryService extends ServiceCore<Category,Long> {
    Category findByName(String name);

    List<CategoryResponse> findAllData();

    Map<String, Object> restore(Long id);

    List<Category> findAllTrash();
}
