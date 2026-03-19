package product;

import java.math.BigDecimal;

public class DigitalProduct extends Product{

    private String downloadLink;

    public DigitalProduct(String name, BigDecimal price, Category category, Manufacturer manufacturer, String downloadLink) {
        super(name, price, category, manufacturer);
        this.downloadLink = downloadLink;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }



    @Override
    public void printProductDetails() {
        System.out.println("Digital Product: " + getName() + " | Download: " + downloadLink);
    }


    @Override
    public void display() {
        System.out.println(getName() + " - " + getPrice());
    }
}

