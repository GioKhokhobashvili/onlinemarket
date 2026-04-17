package com.solvd.market;

import com.solvd.market.customer.Address;
import com.solvd.market.customer.ContactInfo;
import com.solvd.market.customer.Customer;
import com.solvd.market.enums.*;
import com.solvd.market.interfaces.MyDeveloperInfo;
import com.solvd.market.market.Market;
import com.solvd.market.order.Order;
import com.solvd.market.order.OrderItem;
import com.solvd.market.order.PaymentDetails;
import com.solvd.market.order.ShippingDetails;
import com.solvd.market.product.*;
import com.solvd.market.util.WordCounter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        Category electronics = new Category("Electronics", "Devices");
        Manufacturer apple = new Manufacturer("Apple", "USA");
        Product laptop = new PhysicalProduct("Macbook Pro", new BigDecimal("1999.99"), electronics, apple, 2.5, ProductCondition.NEW);
        Product software = new DigitalProduct("Windows 11", new BigDecimal("139.99"), electronics, apple, "Link", ProductCondition.NEW);

        Customer customer = new Customer("Giorgi", "Khokh",
                new Address("Tbilisi", "Kartozia", "0163"),
                new ContactInfo("giorgi@gmail.com", "555"), 25, DiscountTier.GOLD);

        List<OrderItem> cartItems = new ArrayList<>(Arrays.asList(
                new OrderItem(laptop, 2),
                new OrderItem(software, 1)
        ));

        Order firstOrder = new Order(LocalDateTime.now(), customer, cartItems,
                new ShippingDetails(1, "FedEX", "GJE123", ShippingMethod.EXPRESS),
                new PaymentDetails(2, "Credit Card", "Approved", PaymentType.CREDIT_CARD),
                OrderStatus.PENDING);

        Set<Order> allOrders = new HashSet<>(Collections.singletonList(firstOrder));
        Map<String, Customer> customerMap = new HashMap<>();
        customerMap.put(customer.getContactInfo().getEmail(), customer);

        allOrders.stream()
                .filter(order -> !order.getOrderItems().isEmpty())
                .forEach(order -> order.getLocalDateTime());

        List<String> names = cartItems.stream()
                .map(item -> item.getProduct().getName())
                .collect(Collectors.toList());

        customerMap.entrySet().stream()
                .filter(entry -> entry.getKey().equals("giorgi@gmail.com"))
                .map(Map.Entry::getValue)
                .findFirst()
                .ifPresent(foundCustomer -> LOGGER.info("Customer: {}", foundCustomer.getName()));

        boolean expensive = cartItems.stream()
                .anyMatch(item -> item.getProduct().getPrice().compareTo(new BigDecimal("1000")) > 0);

        int totalQty = cartItems.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();

        Set<Product> products = allOrders.stream()
                .flatMap(order -> order.getOrderItems().stream())
                .map(OrderItem::getProduct)
                .collect(Collectors.toSet());

        List<OrderItem> sorted = cartItems.stream()
                .sorted(Comparator.comparing(item -> item.getProduct().getPrice()))
                .collect(Collectors.toList());

        runReflection();

        try (Market market = new Market("Amazon Georgia", allOrders)) {
            firstOrder.processCheckout();
        } catch (Exception e) {
            LOGGER.error("Error: {}", e.getMessage());
        }

        WordCounter.countAndSave(
                "src/main/resources/book.txt",
                "src/main/resources/result.txt"
        );
    }

    public static void runReflection() {
        try {
            Class<?> clazz = Class.forName("product.PhysicalProduct");

            for (Field f : clazz.getDeclaredFields()) {
                LOGGER.info("Field: {} Type: {}", f.getName(), f.getType().getSimpleName());
            }

            for (Constructor<?> c : clazz.getConstructors()) {
                LOGGER.info("Constructor params: {}", c.getParameterCount());
            }

            for (Method m : clazz.getDeclaredMethods()) {
                if (m.isAnnotationPresent(MyDeveloperInfo.class)) {
                    LOGGER.info("Method: {} [Annotation Present]", m.getName());
                } else {
                    LOGGER.info("Method: {}", m.getName());
                }
            }

            Constructor<?> cons = clazz.getConstructor(String.class, BigDecimal.class, Category.class, Manufacturer.class, double.class, ProductCondition.class);
            Object obj = cons.newInstance("Reflected", new BigDecimal("100"), null, null, 1.0, ProductCondition.USED);

            Method disp = clazz.getMethod("display");
            disp.invoke(obj);

        } catch (Exception e) {
            LOGGER.error("Reflection error: {}", e.getMessage());
        }
    }

    public static void executeOrderLogic(List<Order> orders, Predicate<Order> filter, Consumer<Order> action) {
        orders.stream()
                .filter(filter)
                .forEach(action);
    }

    public static void notifyCustomers(List<Order> orders, BiConsumer<Customer, String> notification) {
        orders.forEach(order -> notification.accept(order.getCustomer(), "Received"));
    }
}