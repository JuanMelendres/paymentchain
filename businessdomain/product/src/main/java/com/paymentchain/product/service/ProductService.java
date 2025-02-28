package com.paymentchain.product.service;

import com.paymentchain.product.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public List<Product> getProducts();
    public Optional<Product> getProduct(long id);
    public Product createProduct(Product product);
    public Optional<Product> updateProduct(long id, Product product);
    public Optional<Product> deleteProduct(long id);
}
