package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.extend.ProductDTO;
import org.datn.app.core.dto.ProductSearchRequest;
import org.datn.app.core.entity.Product;
import org.datn.app.core.entity.extend.ProductResponse;
import org.datn.app.core.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/get/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.doDeleteById(id);
    }

    @PutMapping("/update/{id}")
    public Product updateProductById(@PathVariable Long id, @RequestBody Product product) {
        return productService.doUpdateById(product, id);
    }

    @GetMapping("/page/{page}/{size}")
    public Page<Product> getAllProduct(@PathVariable int page, @PathVariable int size) {
        return productService.findAll(page, size);
    }

    @PostMapping(value = "/addDetail")
    public ResponseEntity<?> addProductDetail(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @PostMapping(value = "/addImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> addImage(HttpServletRequest request,MultipartFile file) throws IOException, IOException {
        return productService.addImage(file, request);
    }

    @PostMapping(value = "/findByFilter")
    public Page<Product> findByFilter(@RequestBody ProductSearchRequest model) {
        return productService.findByFilter(model);
    }

    @PostMapping(value = "/findByFilterLike")
    public Page<Product> findByFilterLike(@RequestBody ProductSearchRequest model) {
        return productService.findByFilterLike(model);
    }

    @GetMapping("/findByBrand/{id}")
    public List<Product> findByBrandId(@PathVariable Long id) {
        return productService.findByBrandId(id);
    }

    @GetMapping("/findTop10")
    public List<Product> findTop10ByOrderByIdDesc() {
        return productService.findTop10ByOrderByIdDesc();
    }

    @GetMapping("/findByName/{name}")
    public List<Product> findByName(@PathVariable String name) {
        return productService.findByName(name);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", ex.getMessage());
        data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
