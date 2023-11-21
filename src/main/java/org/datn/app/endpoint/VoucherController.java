package org.datn.app.endpoint;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.dto.VoucherLinkRequest;
import org.datn.app.core.entity.Category;
import org.datn.app.core.entity.Product;
import org.datn.app.core.entity.User;
import org.datn.app.core.entity.Voucher;
import org.datn.app.core.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/voucher")
public class VoucherController {
    private final VoucherService voucherService;

    @PostMapping("/add")
    public ResponseEntity<?> addVoucher(@RequestBody Voucher voucher) {

        return ResponseEntity.ok(voucherService.addVoucher(voucher));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateVoucher(@PathVariable Long id, @RequestBody Voucher voucher) {

        return ResponseEntity.ok(voucherService.doUpdateById(voucher, id));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteVoucher(@PathVariable Long id) {
        voucherService.deleteVoucher(id);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getVoucher(@PathVariable Long id) {
        return ResponseEntity.ok(voucherService.findById(id));
    }

    @GetMapping("/page/{page}/{size}")
    public ResponseEntity<?> getAllVoucher(@PathVariable int page, @PathVariable int size) {
        return ResponseEntity.ok(voucherService.findAll(page, size));
    }


    @GetMapping("/findAll")
    public List<Voucher> findAllVoucher() {
        return voucherService.findAll();
    }

    @GetMapping("/findByCode/{code}")
    public Voucher findByCode(@PathVariable String code) {
        return voucherService.findByCode(code);
    }

    @GetMapping("/findProductByVoucherId/{id}")
    public List<Product> findProductByVoucherId(@PathVariable Long id) {
        return voucherService.findProductByVoucherId(id);
    }

    @GetMapping("/findCategoryByVoucherId/{id}")
    public List<Category> findCategoryByVoucherId(@PathVariable Long id) {
        return voucherService.findCategoryByVoucherId(id);
    }

    @GetMapping("/findAllByVoucherId/{voucherId}")
    public List<User> findAllByVoucherId(@PathVariable Long voucherId) {
        return voucherService.findAllByVoucherId(voucherId);
    }

    @PostMapping("/addVoucherToProduct")
    public Voucher addVoucherToProduct(@RequestBody VoucherLinkRequest model) {
        return voucherService.addVoucherToProduct(model.getVoucherId(), model.getProductIdList());
    }

    @PostMapping("/addVoucherToCategory")
    public Voucher addVoucherToCategory(@RequestBody VoucherLinkRequest model) {
        return voucherService.addVoucherToCategory(model.getVoucherId(), model.getCategoryIdList());
    }

    @PostMapping("/addVoucherToUser")
    public Voucher addVoucherToUser(@RequestBody VoucherLinkRequest model) {
        return voucherService.addVoucherToUser(model.getVoucherId(), model.getUserIdList());
    }

    @GetMapping("/findAllByUserId/{userId}")
    public List<Voucher> findAllByUserId(@PathVariable Long userId) {
        return voucherService.findAllByUserId(userId);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        Map<String, Object> data = new HashMap<>();
        data.put("message", ex.getMessage());
        data.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return ResponseEntity.status(HttpStatus.OK).body(data);
    }
}
