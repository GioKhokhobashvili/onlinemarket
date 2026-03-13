import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args){
        Category electronics = new Category("Electronics", "Devices");
        Manufacturer apple = new Manufacturer("Apple", "USA");
        Product laptop = new Product("Macbook Pro", new BigDecimal("1999.99"), electronics, apple);
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
        System.out.println("Bought: " + market.getOrders()[0].getOrderItems()[0].getProduct().getName());
}
}