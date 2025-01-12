package com.productmanagement.apex.controller;

import com.productmanagement.apex.dto.ProductDto;
import com.productmanagement.apex.model.Product;
import com.productmanagement.apex.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Save a new product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)) }) })
    @PostMapping("/save")
    public ResponseEntity<Mono<Product>> save(@RequestBody ProductDto productDto) throws ExecutionException, InterruptedException {
        Mono<Product> productSave = productService.save(productDto);
        return new ResponseEntity<>(productSave, HttpStatus.OK);
    }

    @Operation(summary = "Find a product by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product founded",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "404", description = "Product not found",
                    content = @Content)})
    @GetMapping("/{productId}")
    public ResponseEntity<Mono<Product>> findById(@PathVariable("productId") String id) {
        Mono<Product> findId = productService.findById(id);
        return new ResponseEntity<>(findId, HttpStatus.OK);
    }

    @Operation(summary = "Find all products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product list",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)) }),
            @ApiResponse(responseCode = "204", description = "There are no products",
                    content = @Content)})
    @GetMapping("/all")
    public Flux<Product> findAll() {
        Flux<Product> products = productService.findAll();
        return products;
    }

    @Operation(summary = "Update a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Product.class)) }) })
    @PutMapping("/update/{productId}")
    public Mono<Product> update(@PathVariable("productId") String id, @RequestBody ProductDto productDto) {
        return productService.update(id, productDto);
    }

    @Operation(summary = "Delete a product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully") })
    @DeleteMapping("/{productId}")
    public void delete(@PathVariable("productId") String id) {
        productService.delete(id).subscribe();
    }

}
