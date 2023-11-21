package org.datn.app.core.service;

import lombok.RequiredArgsConstructor;
import org.datn.app.core.entity.OrderDetail;
import org.datn.app.core.repo.OrderDetailRepo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional(rollbackOn = RuntimeException.class)
public class OrderDetailServiceImpl implements OrderDetailService{
    private final OrderDetailRepo orderDetailRepo;
    @Override
    public OrderDetail doInsert(OrderDetail orderDetail) {
        return orderDetailRepo.save(orderDetail);
    }

    @Override
    public OrderDetail doUpdateById(OrderDetail orderDetail, Long aLong) {
        OrderDetail orderDetail1 = orderDetailRepo.findById(aLong).get();
        orderDetail1.setQuantity(orderDetail.getQuantity());
        orderDetail1.setPrice(orderDetail.getPrice());
        return orderDetailRepo.save(orderDetail1);
    }

    @Override
    public OrderDetail doDeleteById(Long aLong) {
        OrderDetail orderDetail = orderDetailRepo.findById(aLong).get();
        orderDetailRepo.delete(orderDetail);
        return null;
    }

    @Override
    public OrderDetail findById(Long aLong) {
        return orderDetailRepo.findById(aLong).orElse(null);
    }

    @Override
    public List<OrderDetail> findAll() {
        return orderDetailRepo.findAll();
    }

    @Override
    public Page<OrderDetail> findAll(Integer page, Integer size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);
        return orderDetailRepo.findAll(pageable);
    }
}
