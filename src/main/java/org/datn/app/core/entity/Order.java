package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.datn.app.constant.OrderConstant;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
public class Order implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    private String address;

    private String phoneNumber;

    private String name;

    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    private String paymentMethod;

    private String note;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    private Integer status;

    @OneToMany(mappedBy = "order",targetEntity = OrderDetail.class)
    private List<OrderDetail> orderDetails;

    @OneToMany(mappedBy = "order",targetEntity = Transaction.class)
    @JsonIgnore
    private List<Transaction> transactions;

    @PrePersist
    public void prePersist() {
        this.created = new Date();
        this.modified = new Date();
        this.status = OrderConstant.PENDING;
    }

    @PreUpdate
    public void preUpdate() {
        this.modified = new Date();
    }
}
