package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "order_details")
@Setter
@Getter
public class OrderDetail implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "order_detail_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer quantity;
    private Double price;
    @ManyToOne( targetEntity = Order.class , cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne( targetEntity = ProductDetail.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_detail_id")
    private ProductDetail productDetail;
}
