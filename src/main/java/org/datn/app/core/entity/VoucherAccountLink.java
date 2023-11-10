package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name = "voucher_account_link")
public class VoucherAccountLink implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voucher_account_link_id")
    private Long id;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    private Boolean isUsed;

    @ManyToOne(targetEntity = Voucher.class)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;
    private Date created;

    @PrePersist
    public void prePersist() {
        this.created = new Date();
        this.isUsed = false;
    }
}
