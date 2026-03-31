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
import enums.*;
import records.Review;
import interfaces.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.*;

public class Main {
    public static void main(String[] args) {
        Category electronics = new Category("Electronics", "Devices");
        Manufacturer apple = new Manufacturer("Apple", "USA");
        Product laptop = new PhysicalProduct("Macbook Pro", new BigDecimal("1999.99"), electronics, apple, 2.5);
        Product software = new DigitalProduct("Windows 11", new BigDecimal("139.99"), electronics, apple, "www.microsoft.com/download");
        ProductService service = new ProductService();
        Address address = new Address("Tbilisi", "Kartozia", "0163");
        ContactInfo contactInfo = new ContactInfo("giorgi@gmail.com", "+995 555-555-555");
        Customer customer = new Customer("Giorgi", "Khokh", address, contactInfo, 25);
        ShippingDetails shipping = new ShippingDetails(1, "FedEX", "GJE129401240");
        PaymentDetails payment = new PaymentDetails(2, "Credit Card", "Approved");

        OrderItem item1 = new OrderItem(laptop, 2);

        List<OrderItem> cartItems = new ArrayList<>();
        cartItems.add(item1);

        Order firstOrder = new Order(LocalDateTime.now(), customer, cartItems, shipping, payment);

        Set<Order> allOrders = new HashSet<>();
        allOrders.add(firstOrder);

        Map<String, Customer> customerMap = new HashMap<>();
        customerMap.put(customer.getContactInfo().getEmail(), customer);

        Repository<Product> productRepo = new Repository<>();
        productRepo.add(laptop);

        DataWrapper<Customer> customerWrapper = new DataWrapper<>(customer);

        OrderItem firstCartItem = cartItems.get(0);
        for (OrderItem item : cartItems) {
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
        int cartSize = cartItems.size();
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

        Runnable completionTask = () -> System.out.println("Operations finished");
        Supplier<String> sessionSupplier = () -> "Market Session: " + LocalDateTime.now();
        Predicate<Order> hasItems = o -> !o.getOrderItems().isEmpty();
        Consumer<Order> orderLogger = o -> System.out.println("Order for: " + o.getCustomer().getName());
        Function<Product, String> priceTag = p -> p.getName() + " - $" + p.getPrice();
        BiFunction<BigDecimal, PaymentType, BigDecimal> feeCalc = (price, type) -> type.calculateFinalAmount(price);
        BiConsumer<Customer, String> notifier = (c, msg) -> System.out.println(c.getName() + ": " + msg);

        DiscountCalculator discount = (price, pct) -> price.subtract(price.multiply(BigDecimal.valueOf(pct / 100.0)));
        ItemValidator stockCheck = i -> i.getQuantity() > 0;
        OrderAction logStatus = o -> System.out.println("Status: " + OrderStatus.PENDING.getDescription());

        System.out.println(sessionSupplier.get());
        System.out.println(priceTag.apply(laptop));

        BigDecimal discounted = discount.applyDiscount(laptop.getPrice(), DiscountTier.GOLD.getDiscountPercent());
        BigDecimal withFee = feeCalc.apply(discounted, PaymentType.CREDIT_CARD);
        System.out.println("Final price: $" + withFee);

        Review review = new Review("Giorgi", 5, "Excellent");
        System.out.println("Review: " + review.customerName() + " " + review.rating() + "/5");

        executeOrderLogic(new ArrayList<>(allOrders), hasItems, orderLogger);
        notifyCustomers(new ArrayList<>(allOrders), notifier);

        logStatus.perform(firstOrder);
        completionTask.run();
    }

    public static void executeOrderLogic(List<Order> orders, Predicate<Order> filter, Consumer<Order> action) {
        for (Order o : orders) {
            if (filter.test(o)) {
                action.accept(o);
            }
        }
    }

    public static void notifyCustomers(List<Order> orders, BiConsumer<Customer, String> notification) {
        for (Order o : orders) {
            notification.accept(o.getCustomer(), "Your order has been received.");
        }
    }
}