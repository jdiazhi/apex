package com.productmanagement.apex.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public @Data class ProductDto {

    private String productId;
    private String name;
    private BigDecimal price;
    private String category;
}
