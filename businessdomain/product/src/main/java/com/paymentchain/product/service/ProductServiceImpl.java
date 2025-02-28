package com.paymentchain.product.service;

import com.paymentchain.product.entities.Product;
import com.paymentchain.product.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
    
    @Override
    public List<Product> getProducts() {
        log.info("Get products");
        return this.productRepository.findAll();
    }

    @Override
    public Optional<Product> getProduct(long id) {
        log.info("Get product with id {}", id);
        return this.productRepository.findById(id);
    }

    @Override
    public Product createProduct(Product product) {
        log.info("Creating new product: {}", product);

        return this.productRepository.save(product);
    }

    @Override
    public Optional<Product> updateProduct(long id, Product product) {
        Optional<Product> productExist = this.productRepository.findById(id);
        if (productExist.isPresent()) {

            log.info("Updating customer with id {}", product.getId());

            productExist.get().setCode(product.getCode());
            productExist.get().setName(product.getName());

            this.productRepository.save(productExist.get());

            return productExist;
        }
        return Optional.empty();
    }

    @Override
    public Optional<Product> deleteProduct(long id) {
        Optional<Product> productExist = this.productRepository.findById(id);

        if (productExist.isPresent()) {
            log.info("Deleting customer with id: {}", id);
            log.info("Customer: {}", productExist.get().toString());
            this.productRepository.delete(productExist.get());
        }

        return Optional.empty();
    }
}
