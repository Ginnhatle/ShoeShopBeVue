package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.constant.VoucherConstant;
import org.datn.app.core.entity.*;
import org.datn.app.core.entity.extend.VoucherResponse;
import org.datn.app.core.repo.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(rollbackOn = RuntimeException.class)
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {
    private final VoucherRepo voucherRepo;
    private final ProductRepo productRepo;
    private final VoucherProductCategoryLinkRepo voucherProductCategoryLinkRepo;
    private final CategoryRepo categoryRepo;
    private final VoucherAccountLinkDao voucherAccountLinkDao;
    private final UserRepo userRepo;

    @Override
    public Voucher doInsert(Voucher voucher) {
        return voucherRepo.save(voucher);
    }

    @Override
    public Voucher doUpdateById(Voucher voucher, Long aLong) {
        Voucher voucher1 = voucherRepo.findById(aLong).get();
        if (voucher.getName() == null || voucher.getName().equals("")) {
            throw new RuntimeException("Tên voucher không được để trống");
        }
        if(voucher.getEffectFrom() == null){
            throw new RuntimeException("Ngày bắt đầu không được để trống");
        }
        if(voucher.getEffectUntil() == null){
            throw new RuntimeException("Ngày kết thúc không được để trống");
        }
        if(voucher.getEffectFrom().after(voucher.getEffectUntil())){
            throw new RuntimeException("Ngày bắt đầu không được lớn hơn ngày kết thúc");
        }
        if(voucher.getDiscountType() == null){
            throw new RuntimeException("Giảm giá không được để trống");
        }
        if(voucher.getDiscountValue() == null){
            throw new RuntimeException("Giá trị giảm giá không được để trống");
        }
        if(voucher.getDiscountValue() < 0){
            throw new RuntimeException("Giá trị giảm giá không được nhỏ hơn 0");
        }
        if(voucher.getQuantity() == null){
            throw new RuntimeException("Số lượng không được để trống");
        }
        if(voucher.getQuantity() < 0){
            throw new RuntimeException("Số lượng không được nhỏ hơn 0");
        }
        if(voucher.getQuantity() > 100){
            throw new RuntimeException("Số lượng không được lớn hơn 100");
        }
        if(voucher.getLimitPerUser() == null){
            throw new RuntimeException("Số lượng cho mỗi người không được để trống");
        }
        if(voucher.getLimitPerUser() < 0){
            throw new RuntimeException("Số lượng cho mỗi người không được nhỏ hơn 0");
        }
        if(voucher.getLimitPerUser() > 100){
            throw new RuntimeException("Số lượng cho mỗi người không được lớn hơn 100");
        }
        if(voucher.getLimitPerUser() > voucher.getQuantity()){
            throw new RuntimeException("Số lượng cho mỗi người không được lớn hơn số lượng voucher");
        }
        voucher1.setName(voucher.getName());
        voucher1.setEffectFrom(voucher.getEffectFrom());
        voucher1.setEffectUntil(voucher.getEffectUntil());
        voucher1.setQuantity(voucher.getQuantity());
        voucher1.setIsTrash(voucher.getIsTrash());
        voucher1.setDiscountType(voucher.getDiscountType());
        voucher1.setDiscountValue(voucher.getDiscountValue());
        voucher1.setIsAutoApply(voucher.getIsAutoApply());
        voucher1.setLimitPerUser(voucher.getLimitPerUser());
        return voucherRepo.save(voucher1);
    }

    @Override
    public Voucher doDeleteById(Long aLong) {
        Voucher voucher = voucherRepo.findById(aLong).get();
        voucher.setIsTrash(true);
        return voucherRepo.save(voucher);
    }

    @Override
    public Voucher findById(Long aLong) {
        return voucherRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Voucher> findAll() {
        return voucherRepo.findAll();
    }

    @Override
    public Page<Voucher> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return voucherRepo.findAll(pageable);
    }

    @Override
    public Page<VoucherResponse> findAllVoucher(Integer page, Integer size) {
        return null;
    }

    @Override
    public ResponseEntity<?> addVoucher(Voucher voucher) {
        if (voucher.getName() == null || voucher.getName().equals("")) {
            throw new RuntimeException("Tên voucher không được để trống");
        }
        if(voucher.getEffectFrom() == null){
            throw new RuntimeException("Ngày bắt đầu không được để trống");
        }
        if(voucher.getEffectUntil() == null){
            throw new RuntimeException("Ngày kết thúc không được để trống");
        }
        if(voucher.getEffectFrom().after(voucher.getEffectUntil())){
            throw new RuntimeException("Ngày bắt đầu không được lớn hơn ngày kết thúc");
        }
        if(voucher.getDiscountType() == null){
            throw new RuntimeException("Giảm giá không được để trống");
        }
        if(voucher.getDiscountValue() == null){
            throw new RuntimeException("Giá trị giảm giá không được để trống");
        }
        if(voucher.getDiscountValue() < 0){
            throw new RuntimeException("Giá trị giảm giá không được nhỏ hơn 0");
        }
        if(voucher.getQuantity() == null){
            throw new RuntimeException("Số lượng không được để trống");
        }
        if(voucher.getQuantity() < 0){
            throw new RuntimeException("Số lượng không được nhỏ hơn 0");
        }
        if(voucher.getQuantity() > 100){
            throw new RuntimeException("Số lượng không được lớn hơn 100");
        }
        if(voucher.getLimitPerUser() == null){
            throw new RuntimeException("Số lượng cho mỗi người không được để trống");
        }
        if(voucher.getLimitPerUser() < 0){
            throw new RuntimeException("Số lượng cho mỗi người không được nhỏ hơn 0");
        }
        if(voucher.getLimitPerUser() > 100){
            throw new RuntimeException("Số lượng cho mỗi người không được lớn hơn 100");
        }
        if(voucher.getLimitPerUser() > voucher.getQuantity()){
            throw new RuntimeException("Số lượng cho mỗi người không được lớn hơn số lượng voucher");
        }
        voucher.setStatus(VoucherConstant.ACTIVE);
        if(voucherRepo.save(voucher) == null){
            throw new RuntimeException("Thêm voucher thất bại");
        }
        Map result  = new HashMap();
        result.put("message", "Thêm voucher thành công");
        result.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(result);
    }

    @Override
    public Voucher findVoucherByID(Long id) {
        Voucher voucher = voucherRepo.findById(id).orElse(null);
        if(voucher == null){
            throw new RuntimeException("Không tìm thấy voucher");
        }
        return voucher;
    }

    @Override
    public void deleteVoucher(Long id) {
        Voucher voucher = voucherRepo.findById(id).orElse(null);
        if(voucher == null){
            throw new RuntimeException("Không tìm thấy voucher");
        }
        voucher.setIsTrash(true);
        voucherRepo.save(voucher);
    }

    @Override
    public ResponseEntity<?> updateVoucher(Voucher voucher) {
        voucherRepo.save(voucher);
        return ResponseEntity.ok(voucher);
    }

    @Override
    public Voucher findByCode(String code) {
        return voucherRepo.findByCode(code).orElseThrow(
                () -> new RuntimeException("Không tìm thấy voucher")
        );
    }

    @Override
    public List<Product> findProductByVoucherId(Long id) {
        Voucher voucher = voucherRepo.findById(id).orElse(null);
        if(voucher == null){
            throw new RuntimeException("Không tìm thấy voucher");
        }
        List<Product> productList = new ArrayList<>();
        for(VoucherProductCategoryLink item : voucher.getVoucherProductCategoryLinkList()){
            if(item.getProduct() != null){
                productList.add(item.getProduct());
            }
        }
        if(productList == null){
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }
        return productList;
    }

    @Override
    public List<Category> findCategoryByVoucherId(Long id) {
        Voucher voucher = voucherRepo.findById(id).orElse(null);
        if(voucher == null){
            throw new RuntimeException("Không tìm thấy voucher");
        }
        List<Category> categoryList = new ArrayList<>();
       for(VoucherProductCategoryLink item : voucher.getVoucherProductCategoryLinkList()){
              if(item.getCategory() != null){
                  categoryList.add(item.getCategory());
              }
       }
        if(categoryList == null){
            throw new RuntimeException("Không tìm thấy sản phẩm");
        }
        return categoryList;
    }

    @Override
    public Voucher addVoucherToProduct(Long voucherId, List<Long> productId) {
        Voucher voucher = voucherRepo.findById(voucherId).orElse(null);
        if(voucher == null){
            throw new RuntimeException("Không tìm thấy voucher");
        }
        for(Long id : productId){
            VoucherProductCategoryLink voucherProductCategoryLink = new VoucherProductCategoryLink();
            voucherProductCategoryLink.setVoucher(voucher);
            voucherProductCategoryLink.setCategory(null);
            voucherProductCategoryLink.setProduct(productRepo.findById(id).orElseThrow(
                    () -> new RuntimeException("Không tìm thấy sản phẩm")
            ));
            voucherProductCategoryLinkRepo.save(voucherProductCategoryLink);
        }
        return voucher;
    }

    @Override
    public Voucher addVoucherToCategory(Long voucherId, List<Long> categoryId) {
        Voucher voucher = voucherRepo.findById(voucherId).orElse(null);
        if(voucher == null){
            throw new RuntimeException("Không tìm thấy voucher");
        }
        for(Long id : categoryId){
            VoucherProductCategoryLink voucherProductCategoryLink = new VoucherProductCategoryLink();
            voucherProductCategoryLink.setVoucher(voucher);
            voucherProductCategoryLink.setCategory(categoryRepo.findById(id).orElseThrow(
                    () -> new RuntimeException("Không tìm thấy danh mục")
            ));
            voucherProductCategoryLink.setProduct(null);
            voucherProductCategoryLinkRepo.save(voucherProductCategoryLink);
        }
        return voucher;
    }

    @Override
    public void deleteVoucherProduct(Long voucherId, Long productId) {
        Voucher voucher = voucherRepo.findById(voucherId).orElse(null);
        if(voucher == null){
            throw new RuntimeException("Không tìm thấy voucher");
        }
        VoucherProductCategoryLink voucherProductCategoryLink = voucherProductCategoryLinkRepo.findByVoucherIdAndProductId(voucherId, productId);
        if(voucherProductCategoryLink == null){
            throw new RuntimeException("Không tìm thấy voucher");
        }
        voucherProductCategoryLinkRepo.delete(voucherProductCategoryLink);
    }

    @Override
    public void deleteVoucherCategory(Long voucherId, Long categoryId) {
        Voucher voucher = voucherRepo.findById(voucherId).orElse(null);
        if(voucher == null){
            throw new RuntimeException("Không tìm thấy voucher");
        }
        VoucherProductCategoryLink voucherProductCategoryLink = voucherProductCategoryLinkRepo.findByVoucherIdAndCategoryId(voucherId, categoryId);
        if(voucherProductCategoryLink == null){
            throw new RuntimeException("Không tìm thấy voucher");
        }
        voucherProductCategoryLinkRepo.delete(voucherProductCategoryLink);
    }

    @Override
    public List<User> findAllByVoucherId(Long userId) {
        List<User> userList = new ArrayList<>();
        for(VoucherAccountLink item : voucherAccountLinkDao.findAllByVoucherId(userId)){
            userList.add(item.getUser());
        }
        return userList;
    }

    @Override
    public Voucher addVoucherToUser(Long voucherId, List<Long> userId) {
        Voucher voucher = voucherRepo.findById(voucherId).orElse(null);
        if(voucher == null){
            throw new RuntimeException("Không tìm thấy voucher");
        }
        for(Long id : userId){
            VoucherAccountLink voucherAccountLink = new VoucherAccountLink();
            voucherAccountLink.setVoucher(voucher);
            voucherAccountLink.setUser(userRepo.findById(id).orElseThrow(
                    () -> new RuntimeException("Không tìm thấy người dùng")
            ));
            voucherAccountLinkDao.save(voucherAccountLink);
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAllByUserId(Long userId) {
        List<Voucher> voucherList = new ArrayList<>();
        for(VoucherAccountLink item : voucherAccountLinkDao.findAllByUserId(userId)){
            voucherList.add(item.getVoucher());
        }
        return voucherList;
    }
}
