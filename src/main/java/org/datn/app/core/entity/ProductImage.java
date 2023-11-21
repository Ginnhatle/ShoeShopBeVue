package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "product_images")
@Data
public class ProductImage implements Serializable {
    private static final Long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String url;

    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Product product;
}
