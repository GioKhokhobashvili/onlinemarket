package com.solvd.market.order;

import com.solvd.market.exceptions.OutOfStockException;
import com.solvd.market.product.Product;

public class OrderItem {

    private Product product;
    private int quantity;

    public OrderItem(Product product, int quantity) {
        if (quantity <= 0) {
            throw new OutOfStockException("Quantity must be at least 1");
        }
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new OutOfStockException("Quantity must be at least 1");
        }
        this.quantity = quantity;
    }


}
