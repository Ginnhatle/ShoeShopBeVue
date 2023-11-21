package org.datn.app.core.service;

import org.datn.app.core.dto.CartRequest;
import org.datn.app.core.entity.Cart;
import org.datn.app.core.entity.extend.CartDTO;
import org.datn.app.core.service.base.ServiceCore;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface CartService extends ServiceCore<Cart,Long> {
    Map<String,Object> addToCart(CartRequest cartRequest);
    Cart findByUserIdAndAndProductDetailId(Long userId, Long productDetailId);
    ResponseEntity<String> deleteMultiCart(List<Long> cardIdList);
    Page<Cart> findByUserId(CartDTO model);
    Integer getPriceByCartIdList(List<Long> cartIdList,Long voucherId);
}
