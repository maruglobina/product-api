package com.eshopping.product.dashboard.service;

import com.eshopping.product.dashboard.controller.ProductRepository;
import com.eshopping.product.dashboard.model.Product;
import com.eshopping.product.dashboard.model.ProductComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    ProductRepository repository;

    private static final Logger logger = LogManager.getLogger(ProductService.class);

    public Iterable<Product> findAll() {
        logger.info("Finding all the products...");
        return repository.findAllByOrderByIdAsc();
    }

    public Optional<Product> findById(Long id) {
        logger.info("Trying to find a product with id" + id);
        return repository.findById(id);
    }

    public Boolean save(Product product) {
        logger.info("Trying to create a product with name" + product.getName());
        return repository.findById(product.getId())
                .map(p -> {
                    return false;
                })
                .orElseGet(() -> {
                    repository.save(product);
                    return true;
                });
    }

    public Iterable<Product> findProductByCategory(String category){
        return repository.findByCategory(category);
    }

    public List<Product> findProductByCategoryAndAvailability(String category, Boolean availability){
        List<Product> result = repository.findProductByCategoryAndAvailability(category, availability);
        Collections.sort(result, new ProductComparator());
        return result;
    }

    public Optional<Product> update(Long id, Product product){
        return findById(id)
                .map(p -> {
                    p.setRetailPrice(product.getRetailPrice());
                    p.setDiscountedPrice(product.getDiscountedPrice());
                    p.setAvailability(product.getAvailability());
                    return repository.save(p);
                });
    }
}
