package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Indexed;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@Indexed
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", columnDefinition = "TEXT", length = 65535)
    private String name;

    private Double price;

    private Double discount;

    private String material; // chất liệu

    private String model; // kiểu dáng

    private String imageThumbnail;

    private String description;

    private Integer status;

    private Integer gender;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @ManyToOne( targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne( targetEntity = Brand.class)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "product", targetEntity = ProductDetail.class, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProductDetail> productDetails = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, targetEntity = AttributeData.class)
    @JsonIgnore
    private List<AttributeData> attributeData = new ArrayList<>();

    @OneToMany(mappedBy = "product", targetEntity = VoucherProductCategoryLink.class, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<VoucherProductCategoryLink> voucherProductCategoryLinks = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.createdDate = new Date();
    }
}
