package com.productmanagement.apex.service;

import com.productmanagement.apex.dto.ProductDto;
import com.productmanagement.apex.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Mono<Product> save(ProductDto productDto);

    Mono<Product> findById(String id);

    Flux<Product> findAll();

    Mono<Product> update(String id, ProductDto productDto);

    Mono<Void> delete(String id);

}
