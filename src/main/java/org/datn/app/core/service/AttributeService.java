package org.datn.app.core.service;

import org.datn.app.core.dto.AttributeAddAllRequest;
import org.datn.app.core.dto.DeleteAttributeRequest;
import org.datn.app.core.entity.Attribute;
import org.datn.app.core.service.base.ServiceCore;

import java.util.List;
import java.util.Optional;

public interface AttributeService extends ServiceCore<Attribute,Long> {
    Optional<Attribute> findByName(String name);
    List<Attribute> addAll(AttributeAddAllRequest attributeAddAllRequest);
    List<Attribute> findByCategoryId(Long categoryId);
    void deleteAttributeByAttributeId(DeleteAttributeRequest request);
}
