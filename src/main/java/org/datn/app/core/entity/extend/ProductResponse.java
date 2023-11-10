package org.datn.app.core.entity.extend;

import lombok.Getter;
import lombok.Setter;
import org.datn.app.core.entity.Attribute;
import org.datn.app.core.entity.AttributeData;
import org.datn.app.core.entity.Product;
import org.datn.app.core.entity.ProductDetail;

import java.util.List;

@Getter
@Setter
public class ProductResponse extends Product {
    private List<Attribute> attributeList;
    private List<ProductDetail> productDetailList;
    private List<AttributeData> attributeDataList;
}
