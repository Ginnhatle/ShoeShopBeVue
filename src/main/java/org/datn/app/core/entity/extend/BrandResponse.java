package org.datn.app.core.entity.extend;

import lombok.Data;
import org.datn.app.core.entity.Brand;

@Data
public class BrandResponse extends Brand {
    private long totalProduct;
}
