import customer.Address;
import customer.ContactInfo;
import customer.Customer;
import exceptions.PaymentException;
import market.Market;
import order.Order;
import order.OrderItem;
import order.PaymentDetails;
import order.ShippingDetails;
import product.*;
import service.ProductService;
import util.DataWrapper;
import util.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        Category electronics = new Category("Electronics", "Devices");
        Manufacturer apple = new Manufacturer("Apple", "USA");
        Product laptop = new PhysicalProduct("Macbook Pro", new BigDecimal("1999.99"), electronics, apple, 2.5);
        Product software = new DigitalProduct("Windows 11", new BigDecimal("139.99"), electronics, apple, "www.microsoft.com/download");
        ProductService service = new ProductService();
        Address address = new Address("Tbilisi", "Kartozia", "0163");
        ContactInfo contactInfo = new ContactInfo("giorgi@gmail.com", "+995 555-555-555");
        Customer customer = new Customer("Giorgi", "Khokh", address, contactInfo,25);
        ShippingDetails shipping = new ShippingDetails(1, "FedEX", "GJE129401240");
        PaymentDetails payment = new PaymentDetails(2, "Credit Card", "Approved");

        OrderItem item1 = new OrderItem(laptop, 2);

        List<OrderItem> cart = new ArrayList<>();
        cart.add(item1);

        Order firstOrder = new Order(LocalDateTime.now(), customer, cart, shipping, payment);

        Set<Order> allOrders = new HashSet<>();
        allOrders.add(firstOrder);

        Map<String, Customer> customerMap = new HashMap<>();
        customerMap.put(customer.getContactInfo().getEmail(), customer);

        Repository<Product> productRepo = new Repository<>();
        productRepo.add(laptop);

        DataWrapper<Customer> customerWrapper = new DataWrapper<>(customer);

        OrderItem firstCartItem = cart.get(0);
        for (OrderItem item : cart) {
            item.getQuantity();
        }

        Order firstOrderInSet = allOrders.iterator().next();
        for (Order order : allOrders) {
            order.getLocalDateTime();
        }

        Customer firstCustomerInMap = customerMap.values().iterator().next();
        for (Map.Entry<String, Customer> entry : customerMap.entrySet()) {
            entry.getKey();
        }

        boolean isRepoEmpty = productRepo.isEmpty();
        int cartSize = cart.size();
        customerMap.remove("test@gmail.com");

        try (Market market = new Market("Amazon Georgia", allOrders)) {

            System.out.println("Welcome to " + market.getName());
            System.out.println("Customer: " + market.getOrders().iterator().next().getCustomer().getName());

            firstOrder.processCheckout();

        } catch (PaymentException e) {
            System.out.println("Payment Error: " + e.getMessage());
            System.out.println("Please try using a different credit card.");

        } catch (Exception e) {
            System.out.println("SYSTEM ERROR: " + e.getMessage());

        } finally {
            System.out.println("Transaction process finished");
        }

    }
}