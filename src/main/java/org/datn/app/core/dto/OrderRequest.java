package org.datn.app.core.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private List<Long> cartIdList;
    private Long userId;
    private String address;
    private String phoneNumber;
    private Long voucherId;
    private String paymentMethod;
    private String name;
    private String email;
}
