package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "brands")
@Data
public class Brand implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "brand_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`name`")
    private String name;

    @Column(name = "`website`")
    private String website;


    private Boolean isTrash;

    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    private List<Product> products;

    @PrePersist
    public void prePersist() {
        if(this.isTrash == null){
            this.isTrash = false;
        }
        this.isTrash = false;
    }
}
