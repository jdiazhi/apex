package com.productmanagement.apex.service;

import com.productmanagement.apex.dto.MessageProductKafkaDto;
import com.productmanagement.apex.model.Product;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ProductProducer {

    @Value(value = "${message.topic.name}")
    private String topic;
    @Autowired
    private ReactiveKafkaProducerTemplate<String, MessageProductKafkaDto> reactiveKafkaProducerTemplate;

    public void sendMessages(MessageProductKafkaDto product, String eventName) {
        log.info("send to topic={}, {}={},", topic, Product.class.getSimpleName(), product);
        reactiveKafkaProducerTemplate.send(topic, product.getProductId(), product)
                .doOnSuccess(senderResult -> log.info("sent {} offset : {}",
                        product,
                        senderResult.recordMetadata().offset()))
                .subscribe();
    }

}
