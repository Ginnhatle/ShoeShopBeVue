package org.datn.app.core.service;

import com.corundumstudio.socketio.SocketIOServer;
import lombok.RequiredArgsConstructor;
import org.datn.app.constant.AttributeConstant;
import org.datn.app.constant.ProductConstant;
import org.datn.app.constant.ProductDetailConstant;
import org.datn.app.core.dto.ProductSearchRequest;
import org.datn.app.core.dto.SizeDTO;
import org.datn.app.core.entity.*;
import org.datn.app.core.entity.extend.ProductDTO;
import org.datn.app.core.entity.extend.ProductResponse;
import org.datn.app.core.repo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final AttributeRepo attributeRepo;
    private final ProductDetailRepo productDetailRepo;
    private final AttributeDataRepo attributeDataRepo;
    private final BrandRepo brandRepo;
    private final SizeRepo sizeRepo;
    private final ColorRepo colorRepo;
    private final SocketIOServer socketIOServer;
    private final ProductImageRepo productImageRepo;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Product doInsert(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product doUpdateById(Product product, Long aLong) {
        Product product1 = productRepo.findById(aLong).get();
        product1.setName(product.getName());
        product1.setPrice(product.getPrice());
        product1.setDiscount(product.getDiscount());
        product1.setName(product.getName());
        product1.setModel(product.getModel());
        product1.setMaterial(product.getMaterial());
        return productRepo.save(product1);
    }

    @Override
    public Product doDeleteById(Long aLong) {
        Product product = productRepo.findById(aLong).get();
        product.setStatus(0);
        return productRepo.save(product);
    }

    @Override
    public Product findById(Long aLong) {
        return productRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Product> findAll() {
        return productRepo.findAll();
    }

    @Override
    public Page<Product> findAll(Integer page, Integer size) {
        if (page == null) page = 0;
        if (size == null) size = 15;
        if (page < 0) page = 0;
        if (size < 0) size = 15;
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return productRepo.findAll(pageable);
    }

    @Override
    public Page<ProductResponse> findAllProduct(Integer page, Integer size) {
        return null;
    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public ResponseEntity<?> addProduct(ProductDTO productDTO) {
        // Lấy thông tin category và brand từ ID
        Category category = categoryRepo.findById(productDTO.getCategoryId()).orElseThrow(
            () -> new RuntimeException("Danh mục không tồn tại")
        );
        Brand brand = brandRepo.findById(productDTO.getBrandId()).orElseThrow(
            () -> new RuntimeException("Hãng sản xuất không tồn tại")
        );

        // Tạo sản phẩm
        Product product = new Product();
        try {
            if (productDTO.getName() != null && productDTO.getName().length() > 0) {
                product.setName(productDTO.getName());
            }
            try {
                product.setPrice(productDTO.getPrice());
            } catch (Exception e) {
                throw new RuntimeException("Giá sản phẩm không hợp lệ");
            }
            if (product.getDiscount() != null) {
                product.setDiscount(productDTO.getDiscount());
            }
            product.setDiscount(productDTO.getDiscount());
            product.setDescription(productDTO.getDescription());
            product.setCategory(category);
            if (product.getGender() != null) {
                product.setGender(productDTO.getGender());
            }
            product.setMaterial(productDTO.getMaterial());
            product.setModel(productDTO.getModel());
            product.setStatus(ProductConstant.EFFECT);
            product.setGender(productDTO.getGender());
            product.setBrand(brand);
        } catch (RuntimeException e) {
            throw new RuntimeException("có lỗi xảy ra khi thêm sản phẩm");
        }
        List<SizeDTO> sizeList = productDTO.getSizeList();
        if (sizeList.size() == 0) {
            //throw new RuntimeException("Sản phẩm phải có ít nhất 1 kích thước");
        }
        for (int i = 0; i < sizeList.size(); i++) {
            ProductDetail productDetail = new ProductDetail();
            Color color = new Color();
            Color color1 = new Color();
            if (colorRepo.findByName(productDTO.getSizeList().get(i).getColor()) == null) {
                color.setName(productDTO.getSizeList().get(i).getColor());
                color1 = colorRepo.save(color);
            }
            if (sizeRepo.findBySize(sizeList.get(i).getSize()) == null) {
                try {
                    Size size = new Size();
                    size.setSize(productDTO.getSizeList().get(i).getSize());
                    sizeRepo.save(size);
                    productDetail.setSize(sizeRepo.findBySize(size.getSize()));
                    productDetail.setProduct(product);
                    productDetail.setColor(color1);
                    Integer quantity = productDTO.getSizeList().get(i).getQuantity();
                    if (quantity < 0) {
                        throw new RuntimeException("Số lượng không hợp lệ");
                    }
                    productDetail.setQuantity(quantity);
                    product.getProductDetails().add(productDetail);
                    productDetail.setStatus(ProductDetailConstant.EFFECT);
                    productDetailRepo.save(productDetail);
                } catch (RuntimeException e) {
                    throw new RuntimeException("có lỗi xảy ra khi thêm kích thước");
                }
            } else {
                try {
                    productDetail.setSize(sizeRepo.findBySize(sizeList.get(i).getSize()));
                    productDetail.setProduct(product);
                    productDetail.setColor(colorRepo.findByName(productDTO.getSizeList().get(i).getColor()));
                    Integer quantity = productDTO.getSizeList().get(i).getQuantity();
                    if (quantity < 0) {
                        throw new RuntimeException("Số lượng không hợp lệ");
                    }
                    productDetail.setQuantity(quantity);
                    product.getProductDetails().add(productDetail);
                    productDetail.setStatus(ProductDetailConstant.EFFECT);
                    productDetailRepo.save(productDetail);
                } catch (RuntimeException e) {
                    e.printStackTrace();
                    throw new RuntimeException("có lỗi xảy ra khi thêm kích thước");
                }
            }
        }
        // Lưu sản phẩm
        productRepo.save(product);

        // Thêm dữ liệu thuộc tính
        for (Map.Entry<String, String> entry : productDTO.getAttributeValues().entrySet()) {
            Attribute attribute = attributeRepo.findByName(entry.getKey()).orElseThrow(
                () -> new RuntimeException("Thuộc tính không tồn tại")
            );
            switch (attribute.getType()) { // cây validate
                case AttributeConstant.INTEGER: {
                    if (!entry.getValue().matches("\\d+")) {
                        throw new RuntimeException("Giá trị thuộc tính số nguyên hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.STRING: {
                    if (entry.getValue().length() > 255) {
                        throw new RuntimeException("Giá trị thuộc tính chuỗi không hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.DATE: {
                    if (!entry.getValue().matches("\\d{4}-\\d{2}-\\d{2}")) {
                        throw new RuntimeException("Giá trị thuộc tính ngày không hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.BOOLEAN: {
                    if (!entry.getValue().matches("true|false")) {
                        throw new RuntimeException("Giá trị thuộc tính boolean không hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.DOUBLE: {
                    if (!entry.getValue().matches("\\d+\\.\\d+")) {
                        throw new RuntimeException("Giá trị thuộc tính số thực không hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.TIME: {
                    if (!entry.getValue().matches("\\d{2}:\\d{2}:\\d{2}")) {
                        throw new RuntimeException("Giá trị thuộc tính giờ không hợp lệ");
                    }
                    break;
                }
                case AttributeConstant.DATETIME: {
                    if (!entry.getValue().matches("\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}")) {
                        throw new RuntimeException("Giá trị thuộc tính ngày giờ không hợp lệ");
                    }
                    break;
                }
            }
            try {
                AttributeData attributeData = new AttributeData();
                attributeData.setAttribute(attribute);
                attributeData.setProduct(product);
                attributeData.setValue(entry.getValue());
                attributeData.setType(attribute.getType());
                attributeDataRepo.save(attributeData);
            } catch (RuntimeException e) {
                throw new RuntimeException("có lỗi xảy ra khi thêm thuộc tính");
            }
        }
        // Khi thêm thành công sẽ trả về sản phẩm vừa thêm và thông báo thành công
        Map<String, Object> response = new HashMap<>();
        response.put("product", product);
        response.put("message", "Thêm sản phẩm thành công");
        response.put("status", HttpStatus.OK.value());
        // add event connect socket
        socketIOServer.getBroadcastOperations().sendEvent("product", product);
        return ResponseEntity.ok(response);
    }

    @Override
    public List<Product> findByBrandId(Long id) {
        return productRepo.findByBrandId(id);
    }

    @Override
    public ResponseEntity<?> addImage(MultipartFile file, HttpServletRequest request) throws IOException {
        String path_ = "D:\\nginx-1.25.2\\nginx-1.25.2\\html\\image";
        Long id = Long.parseLong(request.getParameter("id"));
        Product product = productRepo.findById(id).orElseThrow(
            () -> new RuntimeException("Sản phẩm không tồn tại")
        );
        // get OS name
        String osName = System.getProperty("os.name");
        Path filePath = null;
        if (osName.toLowerCase().startsWith("windows")) {
            // windows
            filePath = Paths.get(path_, file.getOriginalFilename());
            //filePath = Paths.get("C:\\GitHub\\ShoeShop\\server\\nginx-1.25.3\\html\\image\\product", file.getOriginalFilename());
        } else {
            // linux
            filePath = Paths.get("/var/www/html/image/product", file.getOriginalFilename());
        }
        //Path filePath = Paths.get("C:\\Users\\Admin\\Desktop\\code\\datn_shoe_shop\\src\\server\\html\\image\\product", file.getOriginalFilename());
        product.setImageThumbnail(file.getOriginalFilename());
        if (productRepo.save(product) != null)
            Files.write(filePath, file.getBytes());
        return ResponseEntity.ok(product);
    }

    @Override
    public Page<Product> findByFilter(ProductSearchRequest model) {
        String keyword = model.getKeyword();
        if (keyword == "") {
            keyword = null;
        }
        List<Long> brandIdList = model.getBrandIdList();
        List<Long> categoryIdList = model.getCategoryIdList();
        List<Long> colorIdList = model.getColorIdList();
        List<String> materialList = model.getMaterialList();
        List<String> modelList = model.getModelList();
        List<Long> sizeIdList = model.getSizeIdList();
        List<Long> attributeIdList = model.getAttributeIdList();
        if (model.getPage() == null) {
            model.setPage(0);
        }
        if (model.getSize() == null) {
            model.setSize(30);
        }
        Pageable pageable = PageRequest.of(model.getPage(), model.getSize());
        Page<Product> productPage = this.productRepo.findByFilter(keyword, brandIdList, categoryIdList, attributeIdList, colorIdList, materialList, modelList, sizeIdList, model.getGender(), pageable);
        return productPage;
    }

    @Override
    public Page<Product> findByFilterLike(ProductSearchRequest model) {
        String keyword = model.getKeyword();
        if (keyword == "") {
            keyword = null;
        }
        List<Long> brandIdList = model.getBrandIdList();
        List<Long> categoryIdList = model.getCategoryIdList();
        List<Long> colorIdList = model.getColorIdList();
        List<String> materialList = model.getMaterialList();
        List<String> modelList = model.getModelList();
        List<Long> sizeIdList = model.getSizeIdList();
        List<Long> attributeIdList = model.getAttributeIdList();
        if (model.getPage() == null) {
            model.setPage(0);
        }
        if (model.getSize() == null) {
            model.setSize(30);
        }
        Pageable pageable = PageRequest.of(model.getPage(), model.getSize());
        Page<Product> productPage = this.productRepo.findByFilterLike(keyword, brandIdList, categoryIdList, attributeIdList, colorIdList, materialList, modelList, sizeIdList, pageable);
        return productPage;
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product = productRepo.findById(id).orElseThrow(
            () -> new RuntimeException("Sản phẩm không tồn tại")
        );
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setPrice(product.getPrice());
        productResponse.setDiscount(product.getDiscount());
        productResponse.setBrand(product.getBrand());
        productResponse.setCategory(product.getCategory());
        productResponse.setMaterial(product.getMaterial());
        productResponse.setModel(product.getModel());
        productResponse.setGender(product.getGender());
        productResponse.setProductDetailList(productDetailRepo.findByProductId(id));
        productResponse.setAttributeDataList(product.getAttributeData());
        productResponse.setImageThumbnail(product.getImageThumbnail());
        productResponse.setCreatedDate(product.getCreatedDate());
        productResponse.setImageList(product.getProductImageList().stream().map(ProductImage::getUrl).collect(Collectors.toList()));
        return productResponse;
    }

    @Override
    public List<Product> findTop10ByOrderByIdDesc() {
        List<Product> productList = productRepo.findTop10ByOrderByIdDesc();
        if (productList.size() > 10) {
            productList = productList.subList(0, 10);
        }
        return productList;
    }

    @Override
    public List<Product> findByName(String name) {
        List<Product> productList = productRepo.findAll((root, query, builder) -> {
            return builder.like(root.get("name"), "%" + name + "%");
        });
        return productList;
    }

    @Override
    public void addMultiImage(MultipartFile[] files, HttpServletRequest request) throws IOException {
        String path_ = "D:\\nginx-1.25.2\\nginx-1.25.2\\html\\image";
        try {
            Long id = Long.parseLong(request.getParameter("id"));
            if (files.length == 0) {
                throw new RuntimeException("Không có file nào được chọn");
            }
            if (files.length > 5) {
                throw new RuntimeException("Chỉ được chọn tối đa 5 file");
            }
            for (MultipartFile file : files) {
                // get OS name
                String osName = System.getProperty("os.name");
                Path filePath;
                if (osName.toLowerCase().startsWith("windows")) {
                    filePath = Paths.get(path_, file.getOriginalFilename());
                    // filePath = Paths.get("C:\\GitHub\\ShoeShop\\server\\nginx-1.25.3\\html\\image\\product", file.getOriginalFilename());
                } else {
                    filePath = Paths.get("/var/www/html/image/product", file.getOriginalFilename());
                }
                //Path filePath = Paths.get("C:\\Users\\Admin\\Desktop\\code\\datn_shoe_shop\\src\\server\\html\\image\\product", file.getOriginalFilename());
                ProductImage productImage = new ProductImage();
                productImage.setUrl(file.getOriginalFilename());
                productImage.setProduct(productRepo.findById(id).orElseThrow(
                    () -> new RuntimeException("Sản phẩm không tồn tại")
                ));
                productImageRepo.save(productImage);
                Files.write(filePath, file.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    public ProductDTO getDetail(Long id) {
        ProductDTO productDTO = new ProductDTO();
        Product product = productRepo.findById(id).orElseThrow(
            () -> new RuntimeException("Sản phẩm không tồn tại")
        );
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setDiscount(product.getDiscount());
        productDTO.setBrandId(product.getBrand().getId());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setMaterial(product.getMaterial());
        productDTO.setModel(product.getModel());
        productDTO.setGender(product.getGender());
        productDTO.setImageThumbnail(product.getImageThumbnail());
        productDTO.setCreatedDate(product.getCreatedDate());
        productDTO.setSizeList(product.getProductDetails().stream().map(productDetail -> {
            SizeDTO sizeDTO = new SizeDTO();
            sizeDTO.setSize(productDetail.getSize().getSize() + "");
            sizeDTO.setQuantity(productDetail.getQuantity());
            sizeDTO.setColor(productDetail.getColor().getName());
            return sizeDTO;
        }).collect(Collectors.toList()));
        Map<String, String> attributeValues = new HashMap<>();
        for (AttributeData attributeData : product.getAttributeData()) {
            attributeValues.put(attributeData.getAttribute().getName(), attributeData.getValue());
        }
        // sort attribute by name
        Map<String, String> sortedAttributeValues = new HashMap<>();
        attributeValues.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEachOrdered(x -> sortedAttributeValues.put(x.getKey(), x.getValue()));
        productDTO.setAttributeValues(sortedAttributeValues);
        productDTO.setProductImageList(product.getProductImageList());
        return productDTO;
    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public ProductDTO updateDetail(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepo.findById(id).orElseThrow(
            () -> new RuntimeException("Sản phẩm không tồn tại")
        );

        // Cập nhật thông tin chung của sản phẩm
        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setDiscount(productDTO.getDiscount());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setCategory(categoryRepo.findById(productDTO.getCategoryId()).orElseThrow(
            () -> new RuntimeException("Danh mục không tồn tại")
        ));
        existingProduct.setBrand(brandRepo.findById(productDTO.getBrandId()).orElseThrow(
            () -> new RuntimeException("Hãng sản xuất không tồn tại")
        ));
        existingProduct.setMaterial(productDTO.getMaterial());
        existingProduct.setModel(productDTO.getModel());
        existingProduct.setGender(productDTO.getGender());
        existingProduct.setImageThumbnail(productDTO.getImageThumbnail());
        existingProduct.setCreatedDate(productDTO.getCreatedDate());
        updateSizeInformation(existingProduct, productDTO.getSizeList());
        updateAttributeInformation(existingProduct, productDTO.getAttributeValues());
        productRepo.save(existingProduct);
        Map<String, Object> response = new HashMap<>();
        response.put("product", existingProduct);
        response.put("message", "Cập nhật sản phẩm thành công");
        response.put("status", HttpStatus.OK.value());
        return productDTO;
    }

    private void updateSizeInformation(Product product, List<SizeDTO> newSizeList) {
        // Duyệt qua danh sách ProductDetail hiện tại
        for (ProductDetail productDetail : product.getProductDetails()) {
            // Lấy thông tin kích thước từ newSizeList tương ứng
            SizeDTO newSize = newSizeList.stream()
                .filter(sizeDTO -> sizeDTO.getSize().equals(productDetail.getSize().getSize()))
                .findFirst()
                .orElse(null);

            // Nếu tìm thấy thông tin kích thước mới, cập nhật thông tin kích thước trong ProductDetail
            if (newSize != null) {
                productDetail.setColor(colorRepo.findByName(newSize.getColor()));
                productDetail.setQuantity(newSize.getQuantity());
                // Các cập nhật khác nếu cần
            }
        }

        // Duyệt qua danh sách newSizeList
        for (SizeDTO newSize : newSizeList) {
            Size size = sizeRepo.findBySize(newSize.getSize());
            if (size == null) {
                size = new Size();
                size.setSize(newSize.getSize());
                sizeRepo.save(size);
            }

            Color color = colorRepo.findByName(newSize.getColor());
            if (color == null) {
                color = new Color();
                color.setName(newSize.getColor());
                colorRepo.save(color);
            }

            // Kiểm tra xem có ProductDetail tương ứng đã tồn tại không
            boolean productDetailExists = product.getProductDetails().stream()
                .anyMatch(productDetail -> newSize.getSize().equals(productDetail.getSize().getSize()));

            // Nếu không tìm thấy, thêm mới ProductDetail
            if (!productDetailExists) {
                if (productDetailRepo.findByProductIdAndSizeId(product.getId(), size.getId()).isEmpty() && productDetailRepo.findByProductIdAndColorId(product.getId(), color.getId()).isEmpty()) {
                    ProductDetail productDetail = new ProductDetail();
                    productDetail.setProduct(product);
                    productDetail.setSize(size);
                    productDetail.setColor(color);
                    productDetail.setQuantity(newSize.getQuantity());
                    productDetail.setStatus(ProductDetailConstant.EFFECT);
                    productDetailRepo.save(productDetail);
                } else {
                    throw new RuntimeException("Kích thước hoặc màu sắc đã tồn tại");
                }
            } else {
                // Nếu tìm thấy, cập nhật thông tin kích thước trong ProductDetail
                ProductDetail productDetail = productDetailRepo.findByProductIdAndSizeId(product.getId(), size.getId()).get(0);
                productDetail.setColor(color);
                productDetail.setQuantity(newSize.getQuantity());
                productDetail.setStatus(ProductDetailConstant.EFFECT);
                productDetailRepo.save(productDetail);
            }
        }
    }

    // Hàm cập nhật thông tin thuộc tính của sản phẩm
    private void updateAttributeInformation(Product product, Map<String, String> attributeValues) {
        // Xóa thông tin thuộc tính cũ
        product.getProductDetails().clear();
        // Thêm thông tin thuộc tính mới
        for (Map.Entry<String, String> entry : attributeValues.entrySet()) {
            Attribute attribute = attributeRepo.findByName(entry.getKey()).orElseThrow(
                () -> new RuntimeException("Thuộc tính không tồn tại")
            );
            AttributeData attributeData = new AttributeData();
            attributeData.setAttribute(attribute);
            attributeData.setProduct(product);
            attributeData.setValue(entry.getValue());
            attributeData.setType(attribute.getType());
            attributeDataRepo.save(attributeData);
        }
    }
}
