package com.solvd.market.product;

import com.solvd.market.enums.ProductCondition;
import com.solvd.market.interfaces.MyDeveloperInfo;
import com.solvd.market.interfaces.Taxable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class PhysicalProduct extends Product implements Taxable {

    private static final Logger LOGGER = LogManager.getLogger(PhysicalProduct.class);

    private double weightInKg;

    public PhysicalProduct(String name, BigDecimal price, Category category, Manufacturer manufacturer, double weightInKg, ProductCondition condition) {
        super(name, price, category, manufacturer, condition);
        this.weightInKg = weightInKg;
    }

    public double getWeightInKg() { return weightInKg; }
    public void setWeightInKg(double weightInKg) { this.weightInKg = weightInKg; }

    @Override
    public void printProductDetails() {
        LOGGER.info("Physical Product: {} | Weight: {}kg", getName(), weightInKg);
    }

    @Override
    @MyDeveloperInfo(
            developerName = "Giorgi",
            version = 2
    )
    public void display() {
        LOGGER.info("{} - {}", getName(), getPrice());
    }

    @Override
    public double calculateTaxAmount() {
        return (this.getPrice().doubleValue() * 0.10) + 2.00;
    }
}