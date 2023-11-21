package org.datn.app.core.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.datn.app.core.entity.Attribute;

import java.util.List;

@Getter
@Setter
public class ProductSearchRequest {
    private String keyword;
    private Integer gender;
    private List<Long> brandIdList;
    private List<Long> categoryIdList;
    private List<Long> attributeIdList;
    private List<String>materialList;
    private List<Long> colorIdList;
    private List<String> modelList;
    private List<Long> sizeIdList;
    private Integer page;
    private Integer size;
}
