package org.datn.app.core.dto;

import lombok.Data;
import org.datn.app.core.entity.extend.ProductDTO;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductRequest {
    private ProductDTO product;
    private MultipartFile file;
}
