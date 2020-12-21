package com.vermeg.bookstore.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Embeddable
@Data @AllArgsConstructor @NoArgsConstructor
public class OrderItemKey implements Serializable {

    @Column(nullable=false)
    private Integer orderId;

    @Column(nullable=false)
    private Integer bookId;
}
