package service;

import product.Product;

public class ProductService {

    public void displayAnyProduct(Product product) {
        System.out.println("Starting Product Scan");

        product.printProductDetails();

        System.out.println("----------------");
    }

}