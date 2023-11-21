package org.datn.app.core.dto;

import lombok.Data;

import java.util.List;

@Data
public class AttributeAddAllRequest {
    private Long categoryId;
    private List<Long> attributeIdList;
}
