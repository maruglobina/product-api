package com.eshopping.product.dashboard.controller;

import com.eshopping.product.dashboard.model.Product;
import com.eshopping.product.dashboard.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
public class ProductsController {

    @Autowired
    ProductService service;

    private static final Logger logger = LogManager.getLogger(ProductsController.class);

    @PostMapping("/products")
    ResponseEntity<Object> saveProduct(@RequestBody Product product) {
        if(service.save(product)) return ResponseEntity.created(URI.create("/products/" + product.getId())).build();
        else {
            logger.debug("Could not create product with id: " + product.getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @RequestMapping(value="/products/{product_id}", method = RequestMethod.PUT)
    ResponseEntity<Product> update(@PathVariable(name="product_id") Long id, @RequestBody Product product) {
        return service.update(id, product)
                .map(p -> {
                    return ResponseEntity.status(HttpStatus.OK).body(p);
                })
                .orElseGet(() -> {
                    logger.debug("Could not update product with id: " + id);
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                });
    }

    @GetMapping("/products/{id}")
    ResponseEntity<Product> findProductById(@PathVariable Long id) {
        return service.findById(id)
                .map(p -> {
                    return ResponseEntity.status(HttpStatus.OK).body(p);
                })
                .orElseGet(() -> {
                    logger.debug("Could not find product with id: " + id);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                });
    }

    @RequestMapping(value = "/products", params = "category")
    ResponseEntity<Iterable<Product>> findProductByCategory(@RequestParam String category) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findProductByCategory(category));
    }

    @RequestMapping(value = "/products", params = {"category", "availability"})
    ResponseEntity<Iterable<Product>> findProductByCategoryAndAvailability(@RequestParam String category, @RequestParam Boolean availability) {
        return ResponseEntity.ok().body(service.findProductByCategoryAndAvailability(category, availability));
    }

    @GetMapping("/products")
    ResponseEntity<Iterable<Product>> findAll() {
        return ResponseEntity.ok().body(service.findAll());
    }
}
