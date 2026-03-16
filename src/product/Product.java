package product;

import java.math.BigDecimal;

public abstract class Product {
    private String name;
    private BigDecimal price;
    private Category category;
    private Manufacturer manufacturer;
    public abstract void printProductDetails();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
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

    public Product(String name, BigDecimal price, Category category, Manufacturer manufacturer) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.manufacturer = manufacturer;
    }
}
