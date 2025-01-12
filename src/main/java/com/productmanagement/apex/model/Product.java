package com.productmanagement.apex.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class Product {

    @Id
    private String productId;
    private String name;
    private BigDecimal price;
    private String category;
}
