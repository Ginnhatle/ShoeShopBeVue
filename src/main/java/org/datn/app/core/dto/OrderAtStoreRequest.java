package org.datn.app.core.dto;

import lombok.Getter;
import lombok.Setter;
import org.datn.app.core.entity.Cart;
import org.datn.app.core.entity.ProductDetail;

import java.util.List;

@Setter
@Getter
public class OrderAtStoreRequest {
    private String name;
    private String phoneNumber;
    private List<Cart> cartList;
    private String paymentMethod;
}
