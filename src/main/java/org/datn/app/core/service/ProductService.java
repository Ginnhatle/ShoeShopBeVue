package org.datn.app.core.service;

import org.datn.app.core.dto.ProductSearchRequest;
import org.datn.app.core.entity.Product;
import org.datn.app.core.entity.extend.ProductDTO;
import org.datn.app.core.entity.extend.ProductResponse;
import org.datn.app.core.service.base.ServiceCore;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

public interface ProductService extends ServiceCore<Product, Long> {
    Page<ProductResponse> findAllProduct(Integer page, Integer size);

    ResponseEntity<?> addProduct(ProductDTO productDTO);

    List<Product> findByBrandId(Long id);

    ResponseEntity<?> addImage(MultipartFile file, HttpServletRequest request) throws IOException;

    Page<Product> findByFilter(ProductSearchRequest model);

    Page<Product> findByFilterLike(ProductSearchRequest model);

    ProductResponse getProductById(Long id);

    List<Product> findTop10ByOrderByIdDesc();

    List<Product> findByName(String name);
}
