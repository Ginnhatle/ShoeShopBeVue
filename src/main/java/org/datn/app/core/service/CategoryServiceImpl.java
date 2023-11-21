package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.constant.ProductConstant;
import org.datn.app.core.entity.Category;
import org.datn.app.core.entity.extend.CategoryResponse;
import org.datn.app.core.repo.CategoryRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = RuntimeException.class)
public class CategoryServiceImpl implements CategoryService{
    private final CategoryRepo categoryRepo;
    @Override
    public Category doInsert(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Category doUpdateById(Category category, Long aLong) {
        Category category1 = categoryRepo.findById(aLong).get();
        category1.setName(category.getName());
        category1.setIsTrash(category.getIsTrash());
        category1.setDescription(category.getDescription());
        return categoryRepo.save(category1);
    }

    @Override
    public Category doDeleteById(Long aLong) {
        Category category = categoryRepo.findById(aLong).get();
        category.setIsTrash(true);
        return categoryRepo.save(category);
    }

    @Override
    public Category findById(Long aLong) {
        return categoryRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Category> findAll() {
        return categoryRepo.findAll();
    }

    @Override
    public Page<Category> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return categoryRepo.findAll(pageable);
    }

    @Override
    public Category findByName(String name) {
        return categoryRepo.findByName(name);
    }

    @Override
    public List<CategoryResponse> findAllData() {
        List<CategoryResponse> responseList = new ArrayList<>();
        for (Category item : categoryRepo.findAll()) {
            CategoryResponse response = new CategoryResponse();
            response.setId(item.getId());
            response.setName(item.getName());
            response.setDescription(item.getDescription());
            response.setIsTrash(item.getIsTrash());
            long count =  item.getProducts().stream().filter(product -> product.getStatus() == ProductConstant.EFFECT).count();
            response.setTotalProduct(count);
            responseList.add(response);
        }
        return responseList;
    }

    @Override
    public Map<String, Object> restore(Long id) {
       try{
           Category category = categoryRepo.findById(id).get();
           category.setIsTrash(false);
           Map<String, Object> map = new HashMap<>();
           categoryRepo.save(category);
           map.put("message", "Khôi phục thành công");
           map.put("status", HttpStatus.OK.value());
           return map;
       }catch (RuntimeException e){
              Map<String, Object> map = new HashMap<>();
              map.put("message", "Có lỗi xảy ra vui lòng thử lại sau");
              map.put("status", HttpStatus.BAD_REQUEST.value());
              return map;
       }
    }

    @Override
    public List<Category> findAllTrash() {
        return categoryRepo.findAllByIsTrash(true);
    }
}
