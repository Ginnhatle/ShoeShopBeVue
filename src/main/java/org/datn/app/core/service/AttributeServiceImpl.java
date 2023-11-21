package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.dto.AttributeAddAllRequest;
import org.datn.app.core.dto.DeleteAttributeRequest;
import org.datn.app.core.entity.Attribute;
import org.datn.app.core.entity.Category;
import org.datn.app.core.repo.AttributeRepo;
import org.datn.app.core.repo.CategoryRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = RuntimeException.class)
public class AttributeServiceImpl implements AttributeService {
    private final AttributeRepo attributeRepo;
    private final CategoryRepo categoryRepo;

    @Override
    public Attribute doInsert(Attribute attribute) {
        return attributeRepo.save(attribute);
    }

    @Override
    public Attribute doUpdateById(Attribute attribute, Long aLong) {
        Attribute attribute1 = attributeRepo.findById(aLong).get();
        attribute1.setName(attribute.getName());
        attribute1.setType(attribute.getType());
        return attributeRepo.save(attribute1);
    }

    @Override
    public Attribute doDeleteById(Long aLong) {
        Attribute attribute = attributeRepo.findById(aLong).orElse(null);
        attribute.setIsTrash(true);
        attributeRepo.save(attribute);
        return null;
    }

    @Override
    public Attribute findById(Long aLong) {
        return attributeRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Attribute> findAll() {
        return attributeRepo.findAll();
    }

    @Override
    public Page<Attribute> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return attributeRepo.findAll(pageable);
    }

    @Override
    public Optional<Attribute> findByName(String name) {
        return attributeRepo.findByName(name);
    }

    @Override
    public List<Attribute> addAll(AttributeAddAllRequest attributeAddAllRequest) {
        Category category = categoryRepo.findById(attributeAddAllRequest.getCategoryId()).get();
        List<Attribute> attributeList = attributeRepo.findAllById(attributeAddAllRequest.getAttributeIdList());
        if (category == null) {
            throw new RuntimeException("Category not found");
        } else {
           for(Attribute attribute : attributeList) {
              if(category.getAttributes().contains(attribute)) {
                  throw new RuntimeException("Thuộc tính đã tồn tại");
              }
           }
            Set<Attribute> attributeSet = new HashSet<>();
            attributeSet.addAll(attributeList);
            attributeSet.addAll(category.getAttributes());
            category.setAttributes(attributeSet);
            categoryRepo.save(category);
        }
        return attributeList;
    }

    @Override
    public List<Attribute> findByCategoryId(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).get();
        return category.getAttributes().stream().collect(Collectors.toList());
    }

    @Override
    public void deleteAttributeByAttributeId(DeleteAttributeRequest request) {
        Category category = categoryRepo.findById(request.getCategoryId()).get();
        Attribute attribute = attributeRepo.findById(request.getAttributeId()).get();
        category.getAttributes().remove(attribute);
        categoryRepo.save(category);
    }
}
