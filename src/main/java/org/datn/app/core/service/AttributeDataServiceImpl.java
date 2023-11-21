package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.AttributeData;
import org.datn.app.core.repo.AttributeDataRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = RuntimeException.class)
public class AttributeDataServiceImpl implements AttributeDataService{
    private final AttributeDataRepo attributeDataRepo;
    @Override
    public AttributeData doInsert(AttributeData attributeData) {
        return attributeDataRepo.save(attributeData);
    }

    @Override
    public AttributeData doUpdateById(AttributeData attributeData, Long aLong) {
        AttributeData attributeData1 = attributeDataRepo.findById(aLong).get();
        attributeData1.setValue(attributeData.getValue());
        attributeData1.setType(attributeData.getType());
        return attributeDataRepo.save(attributeData1);
    }

    @Override
    public AttributeData doDeleteById(Long aLong) {
        AttributeData attributeData = attributeDataRepo.findById(aLong).get();
        attributeDataRepo.delete(attributeData);
        return null;
    }

    @Override
    public AttributeData findById(Long aLong) {
        return  attributeDataRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<AttributeData> findAll() {
        return attributeDataRepo.findAll();
    }

    @Override
    public Page<AttributeData> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return attributeDataRepo.findAll(pageable);
    }
}
