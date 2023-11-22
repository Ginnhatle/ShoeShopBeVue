package org.datn.app.core.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = "attribute_data")
public class AttributeData implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "attribute_data_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "attribute_data_value")
    private String value;
    @Column(name = "attribute_data_type")

    private String type;

    @ManyToOne( targetEntity = Attribute.class, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "attribute_id")
    private Attribute attribute;

    @ManyToOne(targetEntity = Product.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

}
