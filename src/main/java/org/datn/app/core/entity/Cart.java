package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "carts")
@Data
public class Cart implements Serializable,Comparable<Cart> {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = ProductDetail.class)
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;

    private Integer quantity;

    private Double price;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Override
    public int compareTo(Cart o) {
        return this.id.compareTo(o.id);
    }
}
