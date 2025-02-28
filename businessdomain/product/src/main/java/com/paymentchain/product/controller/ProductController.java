package com.paymentchain.product.controller;

import com.paymentchain.product.entities.Product;
import com.paymentchain.product.service.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/product")
@Slf4j
public class ProductController {

    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getProducts() {
        try {
            List<Product> products = this.productService.getProducts();
            return new ResponseEntity<>(products, HttpStatus.OK);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable long id) {
        log.info("Get product with id {}", id);
        try {
            Optional<Product> existingCustomer = this.productService.getProduct(id);
            return existingCustomer
                    .map(customer -> new ResponseEntity<>(customer, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping()
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        log.info("Create product {}", product.getId());
        try {
            Product newProduct = this.productService.createProduct(product);
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product) {
        log.info("Update product with id {}", id);
        try {
            Optional<Product> productExist = this.productService.updateProduct(id, product);
            return productExist
                    .map(updatedProduct -> new ResponseEntity<>(updatedProduct, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable long id) {
        log.info("Delete product with id {}", id);
        try {
            Optional<Product> productExist = this.productService.deleteProduct(id);
            return productExist
                    .map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }
        catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
