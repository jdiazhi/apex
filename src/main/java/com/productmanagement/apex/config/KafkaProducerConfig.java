package com.productmanagement.apex.config;

import com.productmanagement.apex.dto.MessageProductKafkaDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import reactor.kafka.sender.SenderOptions;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public SenderOptions<String, MessageProductKafkaDto> producerProps(KafkaProperties kafkaProperties) {
        return SenderOptions.create(kafkaProperties.buildProducerProperties());
    }

    @Bean
    public ReactiveKafkaProducerTemplate<String, MessageProductKafkaDto> reactiveKafkaProducerTemplate(
            SenderOptions<String, MessageProductKafkaDto> producerProps) {
        return new ReactiveKafkaProducerTemplate<>(producerProps);
    }

}
