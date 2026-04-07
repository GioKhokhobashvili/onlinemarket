package com.solvd.market.product;

import com.solvd.market.exceptions.InvalidPriceException;
import com.solvd.market.interfaces.Displayable;
import com.solvd.market.interfaces.Pricable;

import java.math.BigDecimal;

public abstract class Product implements Pricable, Displayable {

    private String name;
    private BigDecimal price;
    private Category category;
    private Manufacturer manufacturer;

    public abstract void printProductDetails();

    public Product(String name, BigDecimal price, Category category, Manufacturer manufacturer) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidPriceException("Price cannot be negative");
        }
        this.name = name;
        this.price = price;
        this.category = category;
        this.manufacturer = manufacturer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if (price != null && price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidPriceException("Price cannot be negative");
        }
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }


}
