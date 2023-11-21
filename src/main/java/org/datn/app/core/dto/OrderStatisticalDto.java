package org.datn.app.core.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class OrderStatisticalDto {
    private Integer brandId;
    private Integer categoryId;
    private String keyword;
    private Integer status;
    private Date fromDate;
    private Date toDate;
    private Integer page;
    private Integer size;
}
