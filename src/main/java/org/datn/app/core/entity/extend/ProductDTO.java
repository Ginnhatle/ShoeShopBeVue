package org.datn.app.core.entity.extend;

import lombok.Data;
import org.datn.app.core.dto.SizeDTO;
import org.datn.app.core.entity.Product;

import java.util.List;
import java.util.Map;

@Data
public class ProductDTO extends Product {
    private Long categoryId;
    private Long brandId;
    private List<SizeDTO> sizeList;
    private Map<String, String> attributeValues;
}

