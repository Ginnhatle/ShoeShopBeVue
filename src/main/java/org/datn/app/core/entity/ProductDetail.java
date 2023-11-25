package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_details")
@Setter
@Getter
public class ProductDetail implements Serializable {
    private static final Long serialVersionUID = 1L;
    private static boolean alreadyToString = false;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_detail_id")
    private Long id;

    @ManyToOne(targetEntity = Size.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "size_id")
    private Size size;

    @ManyToOne(targetEntity = Color.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "color_id")
    private Color color;

    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;
    private Integer quantity;
    private Integer status;

    @OneToMany(mappedBy = "productDetail",targetEntity = Cart.class)
    @JsonIgnore
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "productDetail",targetEntity = OrderDetail.class)
    @JsonIgnore
    private List<OrderDetail> orderDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product",targetEntity = ProductImage.class)
    private List<ProductImage> productImageList = new ArrayList<>();
}
