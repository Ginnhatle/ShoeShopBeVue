package org.datn.app.core.entity.extend;

import lombok.Data;
import org.datn.app.core.entity.Category;

@Data
public class CategoryResponse extends Category {
    private long totalProduct;
}
