package com.eshopping.product.dashboard.model;

import org.apache.commons.lang.builder.CompareToBuilder;

import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {
    @Override
    public int compare(Product product, Product t1) {
        return new CompareToBuilder()
                .append(t1.findDiscountPercentage(), product.findDiscountPercentage())
                .append(product.getDiscountedPrice(), t1.getDiscountedPrice())
                .append(product.getId(), t1.getId())
                .toComparison();
    }
}
