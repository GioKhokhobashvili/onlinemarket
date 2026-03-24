package product;

import interfaces.Taxable;

import java.math.BigDecimal;

public class PhysicalProduct extends Product implements Taxable {

    private double weightInKg;

    public PhysicalProduct(String name, BigDecimal price, Category category, Manufacturer manufacturer, double weightInKg) {
        super(name, price, category, manufacturer);
        this.weightInKg = weightInKg;

    }

    public double getWeightInKg() {
        return weightInKg;
    }

    public void setWeightInKg(double weightInKg) {
        this.weightInKg = weightInKg;
    }

    @Override
    public void printProductDetails() {
        System.out.println("Physical Product: " + getName() + " | Weight: " + weightInKg + "kg");

    }

    @Override
    public void display() {
        System.out.println(getName() + " - " + getPrice());
    }

    @Override
    public double calculateTaxAmount() {
        return (this.getPrice().doubleValue() * 0.10) + 2.00;
    }
}