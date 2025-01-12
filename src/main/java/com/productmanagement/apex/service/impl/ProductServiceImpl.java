package com.productmanagement.apex.service.impl;

import com.productmanagement.apex.dto.MessageProductKafkaDto;
import com.productmanagement.apex.dto.ProductDto;
import com.productmanagement.apex.model.Product;
import com.productmanagement.apex.repository.ProductRepository;
import com.productmanagement.apex.service.ProductProducer;
import com.productmanagement.apex.service.ProductService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @NonNull
    private ProductRepository productRepository;

    @Autowired
    private ProductProducer producer;

    @Override
    public Mono<Product> save(ProductDto productDto) {
        if(Objects.nonNull(productDto)){
            Mono<Product> productMono = productRepository.save(productDtoToEntity(productDto));
            return productMono.flatMap(product -> sendToKafka(product, "create"));
        }
        return null;
    }

    public Mono<Product> sendToKafka(Product product, String eventName){
        MessageProductKafkaDto messageProductKafkaDto = MessageProductKafkaDto.builder()
                .productId(product.getProductId())
                .createAt(new Timestamp(new Date().getTime()))
                .eventName(eventName)
                .build();
        producer.sendMessages(messageProductKafkaDto, eventName);
        return Mono.just(product);
    }

    @Override
    public Mono<Product> findById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> update(String id, ProductDto productDto) {
        productDto.setProductId(id);
        return this.productRepository.findById(id)
                .map(product -> productDtoToEntity(productDto))
                .flatMap(this.productRepository::save);
    }

    @Override
    public Mono<Void> delete(String id) {
        log.info("Delete id {}", id);
        return productRepository.deleteById(id);
    }

    public static Product productDtoToEntity(ProductDto productDto){
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
