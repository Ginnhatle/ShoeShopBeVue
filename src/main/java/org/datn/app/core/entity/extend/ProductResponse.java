package org.datn.app.core.entity.extend;

import lombok.Getter;
import lombok.Setter;
import org.datn.app.core.entity.*;

import java.util.List;

@Getter
@Setter
public class ProductResponse extends Product {
    private List<Attribute> attributeList;
    private List<ProductDetail> productDetailList;
    private List<String> imageList;
    private List<AttributeData> attributeDataList;
}
