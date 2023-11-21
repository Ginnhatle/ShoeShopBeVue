package org.datn.app.core.dto;

import lombok.Getter;
import lombok.Setter;
import org.datn.app.core.entity.User;

import java.util.List;

@Setter
@Getter
public class VoucherLinkRequest {
    private Long voucherId;
    private List<Long> productIdList;
    private List<Long> categoryIdList;
    private List<Long> userIdList;
}
