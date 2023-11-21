package org.datn.app.core.dto;

import lombok.Data;

import java.util.List;

@Data
public class CartRequest {
    private Long productDetailId;
    private Integer quantity;
    private Long userId;
    private List<Long> cartIdList;
    private Long voucherId;
}
