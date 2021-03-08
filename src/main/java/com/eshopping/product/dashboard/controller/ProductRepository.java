package com.eshopping.product.dashboard.controller;

import com.eshopping.product.dashboard.model.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("select p from Product p where p.category = :category order by availability desc, discounted_price asc, id asc")
    Iterable<Product> findByCategory(String category);

    List<Product> findProductByCategoryAndAvailability(@Param("category") String category, @Param("availability") Boolean availability);

    Iterable<Product> findAllByOrderByIdAsc();
}
