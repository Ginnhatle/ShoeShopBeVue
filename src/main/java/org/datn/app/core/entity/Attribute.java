package org.datn.app.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "attributes")
@Data
public class Attribute implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "attribute_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "`name`")
    private String name;

    private String type;

    private Boolean isTrash;

    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @OneToMany(mappedBy = "attribute")
    @JsonIgnore
    private List<AttributeData> attributeDataList;

    @PrePersist
    public void prePersist() {
        this.created = new Date();
        this.isTrash = false;
    }

}
