package org.datn.app.core.entity;

import lombok.Data;
import org.datn.app.constant.VoucherConstant;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "voucher_product_category_link")
@Data
public class VoucherProductCategoryLink implements java.io.Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_product_category_link_id")
    private Long id;

    @ManyToOne(targetEntity = Product.class)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(targetEntity = Voucher.class)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

    @ManyToOne(targetEntity = Category.class)
    @JoinColumn(name = "category_id")
    private Category category;

    private Integer type;

    private Date created;

    private Integer status;

    @PrePersist
    public void prePersist() {
        this.created = new Date();
        this.status = VoucherConstant.ACTIVE;
    }
}
