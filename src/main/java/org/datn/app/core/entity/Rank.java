package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "ranks")
@Data
public class Rank implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "rank_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rank_name" ,columnDefinition = "TEXT(255)")
    private String name;

    @Column(name = "rank_condition")
    private Double condition;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "target_money")
    private Double targetMoney;

    @OneToMany(mappedBy = "rank")
    @JsonIgnore
    private List<User> users;
}
