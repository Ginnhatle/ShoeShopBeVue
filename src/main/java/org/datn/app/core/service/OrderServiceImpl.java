package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.datn.app.constant.OrderConstant;
import org.datn.app.constant.PaymentMethodConstant;
import org.datn.app.core.dto.OrderRequest;
import org.datn.app.core.dto.OrderStatisticalDto;
import org.datn.app.core.entity.*;
import org.datn.app.core.repo.*;
import org.datn.app.util.GenerateString;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Transactional(rollbackOn = RuntimeException.class)
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderDetailRepo orderDetailRepo;
    private final CartRepo cartRepo;
    private final OrderRepo orderRepo;
    private final VoucherRepo voucherRepo;
    private final UserRepo userRepo;
    private final TransactionRepo transactionRepo;
    private final ProductDetailRepo productDetailRepo;

    @Override
    public Order doInsert(Order order) {
        return orderRepo.save(order);
    }

    @Override
    public Order doUpdateById(Order order, Long aLong) {
        Order order1 = orderRepo.findById(aLong).get();
        order1.setAddress(order.getAddress());
        order1.setPhoneNumber(order.getPhoneNumber());
        return orderRepo.save(order1);
    }

    @Override
    public Order doDeleteById(Long aLong) {
        Order order = orderRepo.findById(aLong).get();
        orderRepo.delete(order);
        return null;
    }

    @Override
    public Order findById(Long aLong) {
        return orderRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<Order> findAll() {
        return orderRepo.findAll();
    }

    @Override
    public Page<Order> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return orderRepo.findAll(pageable);
    }

    @Override
    public Page<Order> findAllByStatus(OrderStatisticalDto model) {
        Pageable pageable = Pageable.ofSize(model.getSize()).withPage(model.getPage());
        if(model.getKeyword() != null && model.getKeyword().equals("")){
            model.setKeyword(null);
        }
        if(model.getFromDate() == null){
            model.setFromDate(new Date(0));
        }
        if(model.getToDate() == null){
            model.setToDate(new Date());
        }
        // so sánh  ngày bắt đầu và ngày kết thúc
        if(model.getFromDate().compareTo(model.getToDate()) > 0){
            throw new RuntimeException("Ngày bắt đầu không thể lớn hơn ngày kết thúc");
        }
        Page<Order> orderPage = orderRepo.findAll((root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList();
            if(model.getKeyword() != null){
                predicates.add(builder.or(
                        builder.like(root.get("code"), "%" + model.getKeyword() + "%"),
                        builder.like(root.get("address"), "%" + model.getKeyword() + "%"),
                        builder.like(root.get("phoneNumber"), "%" + model.getKeyword() + "%"),
                        builder.like(root.get("name"), "%" + model.getKeyword() + "%"),
                        builder.like(root.get("email"), "%" + model.getKeyword() + "%")
                ));
            }
            if(model.getStatus() != null){
                predicates.add(builder.equal(root.get("status"), model.getStatus()));
            }
            if(model.getFromDate() != null){
                predicates.add(builder.greaterThanOrEqualTo(root.get("created"), model.getFromDate()));
            }
            if(model.getToDate() != null){
                predicates.add(builder.lessThanOrEqualTo(root.get("created"), model.getToDate()));
            }
            return builder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
        return orderPage;
    }

    @Override
    public ResponseEntity<?> deliveryOrder(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Đơn hàng không tồn tại")
        );
        if (order.getStatus().equals(OrderConstant.PENDING)) {
            throw new RuntimeException("Đơn hàng không thể giao");
        }
        order.setStatus(OrderConstant.DELIVERING);
        orderRepo.save(order);
        Map response = new HashMap();
        response.put("message", "Đơn hàng đang tiến hành giao");
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);

    }

    @Override
    public ResponseEntity<?> receiveOrder(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Đơn hàng không tồn tại")
        );
        if (order.getStatus().equals(OrderConstant.PENDING)) {
            throw new RuntimeException("Đơn hàng không thể nhận");
        }
        order.setStatus(OrderConstant.RECEIVED);
        orderRepo.save(order);
        Map response = new HashMap();
        response.put("message", "Nhận hàng thành công");
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> detailOrder(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Đơn hàng không tồn tại")
        );
        List<OrderDetail> orderDetailList = orderDetailRepo.findAllByOrderId(id);
        Map response = new HashMap();
        response.put("order", order);
        response.put("orderDetailList", orderDetailList);
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(response);
    }

    @Override
    public Order findFirstByUserId(Long userId) {
        Collection<Order> orderList = orderRepo.findByUserId(userId);
        if (orderList.size() == 0) {
            return null;
        }
        return orderList.stream().findFirst().get();
    }

    @Override
    public Order findByCode(String code) {
        return orderRepo.findByCode(code);
    }

    @Override
    public Order findByCodeAndStatus(String code, String status) {
        return orderRepo.findByCodeAndStatus(code, status);
    }

    @Override
    @Transactional(rollbackOn = RuntimeException.class)
    public Map<String, Object> doOrder(OrderRequest model) {
        if (orderRepo.findByUserIdAndStatus(model.getUserId(), OrderConstant.PENDING).size() > 0) {
            throw new RuntimeException("Bạn đã có đơn hàng đang chờ xử lý");
        }
        List<Cart> cartList = cartRepo.findAllById(model.getCartIdList());
        User user = userRepo.findById(model.getUserId()).orElseThrow(
                () -> new RuntimeException("Người dùng không tồn tại")
        );
        AtomicReference<Double> total = new AtomicReference<>((double) 0);
        Order order = new Order();
        if(model.getAddress() == null || model.getAddress().equals("")){
            throw new RuntimeException("Địa chỉ không được để trống");
        }
        if(model.getPhoneNumber() == null || model.getPhoneNumber().equals("")){
            throw new RuntimeException("Số điện thoại không được để trống");
        }
        if(model.getName() == null || model.getName().equals("")){
            throw new RuntimeException("Tên người nhận không được để trống");
        }
        if(model.getEmail() == null || model.getEmail().equals("")){
            throw new RuntimeException("Email không được để trống");
        }
        // regex kiểm tra định dạng email
        if(!model.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")){
            throw new RuntimeException("Email không đúng định dạng");
        }
        // regex kiểm tra định dạng số điện thoại
//        if(!model.getPhoneNumber().matches("^[0-9]{10}+$")){
//            throw new RuntimeException("Số điện thoại không đúng định dạng");
//        }
        order.setAddress(model.getAddress());
        order.setPhoneNumber(model.getPhoneNumber());
        order.setName(model.getName());
        order.setEmail(model.getEmail());
        order.setCode(GenerateString.generateString(10));
        if (model.getPaymentMethod().equals(PaymentMethodConstant.BANK_TRANSFER)) {
            order.setPaymentMethod(PaymentMethodConstant.BANK_TRANSFER);
            order.setStatus(OrderConstant.PENDING_BANK_TRANSFER);
        } else if (model.getPaymentMethod().equals(PaymentMethodConstant.COD)) {
            order.setPaymentMethod(PaymentMethodConstant.COD);
            order.setStatus(OrderConstant.PENDING);
        }
        order.setNote(OrderConstant.ORDER_NEW);
        order.setUser(user);
        // TODO: Đặt hàng và thêm data vào bảng order_detail
        try {
            orderRepo.save(order);
            cartList.forEach(cart -> {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                Integer quantity = cart.getQuantity();
                if (quantity == null || quantity == 0) {
                    throw new RuntimeException("Số lượng sản phẩm không được để trống");
                }
                if (quantity > cart.getProductDetail().getQuantity()) {
                    throw new RuntimeException("Số lượng sản phẩm không đủ");
                }
                orderDetail.setProductDetail(cart.getProductDetail());
                orderDetail.setQuantity(quantity);
                orderDetail.setPrice(cart.getPrice());
                if (orderDetailRepo.save(orderDetail) != null) {
                    // TODO : Xóa cart sau khi đặt hàng thành công
                    total.updateAndGet(v -> v + orderDetail.getPrice() * orderDetail.getQuantity());
                    cartRepo.delete(cart);
                }
            });

            // TODO: Thêm data vào bảng Transaction

            try {
                Transaction transaction = new Transaction();
                transaction.setOrder(order);
                transaction.setAddress(model.getAddress());
                transaction.setPhoneNumber(model.getPhoneNumber());
                transaction.setCode(GenerateString.generateString(10));

                // TODO : Thêm phí ship

                if (total.get() > OrderConstant.DELIVERY_FEE_FREE_MIN) {
                    transaction.setDeliveryFee(OrderConstant.DELIVERY_FEE_FREE);
                } else {
                    transaction.setDeliveryFee(OrderConstant.DELIVERY_FEE);
                }
                transaction.setTotal(total.get() + transaction.getDeliveryFee());
                transaction.setVoucher(voucherRepo.findById(model.getVoucherId()).orElse(null));
                transactionRepo.save(transaction);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("Có lỗi xảy ra khi đặt hàng");
            }
            Map<String, Object> data = new HashMap<>();
            data.put("message", "Đặt hàng thành công");
            data.put("status", HttpStatus.OK.value());
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Có lỗi xảy ra khi đặt hàng");
        }
    }

    @Override
    public ResponseEntity<?> approveOrder(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Đơn hàng không tồn tại")
        );
        // TODO: Duyệt đơn hàng
        synchronized (order) {
            if (order.getStatus().equals(OrderConstant.PENDING)
                    || order.getStatus().equals(OrderConstant.PENDING_BANK_TRANSFER)) {
                order.setStatus(OrderConstant.CONFIRMED);
                order.setCreated(new Date());
                orderRepo.save(order);
                List<OrderDetail> orderDetailList = order.getOrderDetails();
                // TODO: Trừ số lượng sản phẩm trong kho
                orderDetailList.forEach(orderDetail -> {
                    ProductDetail productDetail = orderDetail.getProductDetail();
                    productDetail.setQuantity(productDetail.getQuantity() - orderDetail.getQuantity());
                    productDetailRepo.save(productDetail);
                });
                Map<String, Object> data = new HashMap<>();
                data.put("message", "Duyệt đơn hàng thành công");
                data.put("status", HttpStatus.OK.value());
                return ResponseEntity.ok(data);
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Đơn hàng đã được duyệt");
        data.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(data);
    }

    @Override
    public ResponseEntity<?> cancelOrder(Long id) {
        Order order = orderRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Đơn hàng không tồn tại")
        );
        synchronized (order) {
            if (order.getStatus().equals(OrderConstant.PENDING)
                    || order.getStatus().equals(OrderConstant.PENDING_BANK_TRANSFER)) {
                order.setStatus(OrderConstant.CANCELLED);
                orderRepo.save(order);
                Map<String, Object> data = new HashMap<>();
                data.put("message", "Hủy đơn hàng thành công");
                data.put("status", HttpStatus.OK.value());
                return ResponseEntity.ok(data);
            } else if (order.getStatus().equals(OrderConstant.CONFIRMED)) {
                Map<String, Object> data = new HashMap<>();
                data.put("message", "Đơn hàng đã được duyệt không thể hủy");
                data.put("status", HttpStatus.OK.value());
                return ResponseEntity.ok(data);
            }
        }
        Map<String, Object> data = new HashMap<>();
        data.put("message", "Đơn hàng đã được hủy");
        data.put("status", HttpStatus.OK.value());
        return ResponseEntity.ok(data);
    }

    @Override
    public ResponseEntity<?> returnOrder(Long id, String note) {
        Order order = orderRepo.findById(id).orElseThrow(
                () -> new RuntimeException("Đơn hàng không tồn tại")
        );
        // đơn hàng quá 7 ngày không được trả
        if (order.getModified().getTime() + 7 * 24 * 60 * 60 * 1000 < new Date().getTime()) {
            Map<String, Object> data = new HashMap<>();
            data.put("message", "Đơn hàng đã quá 7 ngày không thể trả lại");
            data.put("status", HttpStatus.OK.value());
            return ResponseEntity.ok(data);
        }
        synchronized (order) {
            if (order.getStatus().equals(OrderConstant.CONFIRMED)) {
                order.setStatus(OrderConstant.RETURNED);
                order.setNote(note);
                orderRepo.save(order);
                List<OrderDetail> orderDetailList = order.getOrderDetails();
                orderDetailList.forEach(orderDetail -> {
                    ProductDetail productDetail = orderDetail.getProductDetail();
                    productDetail.setQuantity(productDetail.getQuantity() + orderDetail.getQuantity());
                    productDetailRepo.save(productDetail);
                });
                Map<String, Object> data = new HashMap<>();
                data.put("message", "Trả đơn hàng thành công");
                data.put("status", HttpStatus.OK.value());
                return ResponseEntity.ok(data);
            } else {
                Map<String, Object> data = new HashMap<>();
                data.put("message", "Đơn hàng chưa được duyệt không thể trả");
                data.put("status", HttpStatus.OK.value());
                return ResponseEntity.ok(data);
            }
        }
    }

    @Override
    public Page<Order> findAllByUserId(Long userId, Pageable pageable) {
        return orderRepo.findAllByUserId(userId, pageable);
    }

}
