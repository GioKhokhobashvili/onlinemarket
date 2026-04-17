package com.solvd.market.product;

import com.solvd.market.enums.ProductCondition;
import com.solvd.market.interfaces.Taxable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

public class DigitalProduct extends Product implements Taxable {

    private static final Logger LOGGER = LogManager.getLogger(DigitalProduct.class);

    private String downloadLink;

    public DigitalProduct(String name, BigDecimal price, Category category, Manufacturer manufacturer, String downloadLink, ProductCondition condition) {
        super(name, price, category, manufacturer, condition);
        this.downloadLink = downloadLink;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    @Override
    public double calculateTaxAmount() {
        return this.getPrice().doubleValue() * 0.05;
    }

    @Override
    public void printProductDetails() {
        LOGGER.info("Digital Product: {} | Download: {}", getName(), downloadLink);
    }

    @Override
    public void display() {
        LOGGER.info("{} - {}", getName(), getPrice());
    }
}