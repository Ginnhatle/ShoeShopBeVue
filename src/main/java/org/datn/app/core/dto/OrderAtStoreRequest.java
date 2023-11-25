package org.datn.app.core.dto;

import lombok.Getter;
import lombok.Setter;
import org.datn.app.core.entity.ProductDetail;

import java.util.List;

@Setter
@Getter
public class OrderAtStoreRequest {
    private String name;
    private String phoneNumber;
    private List<ProductDetail> productDetailList;
    private String paymentMethod;
}
