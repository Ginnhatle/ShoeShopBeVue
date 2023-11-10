package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Table(name = "transactions")
@Entity
@Data
public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "transaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    private String address;

    private String phoneNumber;

    private Long deliveryFee;

    private Double total;

    @ManyToOne(targetEntity = Order.class)
    @JoinColumn(name = "order_id")
    private Order order;

   // one transaction have many voucher
    @ManyToOne(targetEntity = Voucher.class)
    @JoinColumn(name = "voucher_id")
    private Voucher voucher;

}
