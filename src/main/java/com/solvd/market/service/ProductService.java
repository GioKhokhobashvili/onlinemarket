package com.solvd.market.service;

import com.solvd.market.product.Product;

public class ProductService {

    public void displayAnyProduct(Product product) {
        System.out.println("Starting Product Scan");

        product.printProductDetails();

        System.out.println("----------------");
    }

}