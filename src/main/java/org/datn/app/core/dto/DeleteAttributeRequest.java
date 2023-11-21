package org.datn.app.core.dto;

import lombok.Data;

@Data
public class DeleteAttributeRequest {
    private Long categoryId;
    private Long attributeId;
}
