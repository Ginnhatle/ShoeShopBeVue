package org.datn.app.core.entity.extend;

import lombok.Data;
import org.datn.app.core.entity.Voucher;

import java.util.Date;

@Data
public class VoucherResponse  extends Voucher {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String image;
    private Date effectFrom;
    private Date effectUntil;
    private Date created;
    private Integer quantity;
    private Integer limitPerUser;
    private Integer discountType;
    private Integer discountValue;
    private Boolean isAutoApply;
    private Integer status;
}
