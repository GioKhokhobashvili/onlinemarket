import customer.Address;
import customer.ContactInfo;
import customer.Customer;
import market.Market;
import order.Order;
import order.OrderItem;
import order.PaymentDetails;
import order.ShippingDetails;
import product.*;
import service.ProductService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args){
        Category electronics = new Category("Electronics", "Devices");
        Manufacturer apple = new Manufacturer("Apple", "USA");
        Product laptop = new PhysicalProduct("Macbook Pro", new BigDecimal("1999.99"), electronics, apple, 2.5);
        Product software = new DigitalProduct("Windows 11", new BigDecimal("139.99"), electronics, apple, "www.microsoft.com/download");
        ProductService service = new ProductService();
        Address address = new Address("Tbilisi", "Kartozia", "0163");
        ContactInfo contactInfo = new ContactInfo("giorgi@gmail.com", "+995 555-555-555");
        Customer customer = new Customer("Giorgi", "Khokh", address, contactInfo);
        ShippingDetails shipping = new ShippingDetails("FedEX", "GJE129401240");
        PaymentDetails payment = new PaymentDetails("Credit Card", "Approved");
        OrderItem item1 = new OrderItem(laptop, 2);
        OrderItem[] cart = {item1};
        Order firstOrder = new Order (LocalDateTime.now(), customer, cart, shipping, payment);
        Order[] allOrders = { firstOrder };
        Market market = new Market("Amazon Georgia", allOrders);
        System.out.println("Welcome to " + market.getName());
        System.out.println("Customer: " + market.getOrders()[0].getCustomer().getName());
        service.displayAnyProduct(laptop);
        service.displayAnyProduct(software);
}
}