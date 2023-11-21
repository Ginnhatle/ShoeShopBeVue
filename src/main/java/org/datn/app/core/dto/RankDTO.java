package org.datn.app.core.dto;

import lombok.Data;

@Data
public class RankDTO {
    private Long id;
    private String name;
    private Double condition;
    private Double discount;
    private Double targetMoney;
}
