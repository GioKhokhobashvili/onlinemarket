package product;

import java.math.BigDecimal;

public class PhysicalProduct extends Product {

    private double weightInKg;

    public double getWeightInKg() {
        return weightInKg;
    }

    public void setWeightInKg(double weightInKg) {
        this.weightInKg = weightInKg;
    }

    public PhysicalProduct(String name, BigDecimal price, Category category, Manufacturer manufacturer, double weightInKg) {
        super(name, price, category, manufacturer);
        this.weightInKg = weightInKg;

    }

    @Override
    public void printProductDetails() {
        System.out.println("Physical Product: " + getName() + " | Weight: " + weightInKg + "kg");

    }
}
