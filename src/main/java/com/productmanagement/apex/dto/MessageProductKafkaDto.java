package com.productmanagement.apex.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class MessageProductKafkaDto {

    private String productId;
    private Timestamp createAt;
    private String eventName;

}
