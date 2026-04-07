package com.solvd.market;

import com.solvd.market.customer.Address;
import com.solvd.market.customer.ContactInfo;
import com.solvd.market.customer.Customer;
import com.solvd.market.interfaces.MyDeveloperInfo;
import com.solvd.market.market.Market;
import com.solvd.market.order.Order;
import com.solvd.market.order.OrderItem;
import com.solvd.market.order.PaymentDetails;
import com.solvd.market.order.ShippingDetails;
import com.solvd.market.product.*;

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
    public static void main(String[] args) {
        Category electronics = new Category("Electronics", "Devices");
        Manufacturer apple = new Manufacturer("Apple", "USA");
        Product laptop = new PhysicalProduct("Macbook Pro", new BigDecimal("1999.99"), electronics, apple, 2.5);
        Product software = new DigitalProduct("Windows 11", new BigDecimal("139.99"), electronics, apple, "Link");

        Customer customer = new Customer("Giorgi", "Khokh",
                new Address("Tbilisi", "Kartozia", "0163"),
                new ContactInfo("giorgi@gmail.com", "555"), 25);

        List<OrderItem> cartItems = new ArrayList<>(Arrays.asList(
                new OrderItem(laptop, 2),
                new OrderItem(software, 1)
        ));

        Order firstOrder = new Order(LocalDateTime.now(), customer, cartItems,
                new ShippingDetails(1, "FedEX", "GJE123"),
                new PaymentDetails(2, "Credit Card", "Approved"));

        Set<Order> allOrders = new HashSet<>(Collections.singletonList(firstOrder));
        Map<String, Customer> customerMap = new HashMap<>();
        customerMap.put(customer.getContactInfo().getEmail(), customer);

        allOrders.stream()
                .filter(o -> !o.getOrderItems().isEmpty())
                .forEach(o -> o.getLocalDateTime());

        List<String> names = cartItems.stream()
                .map(i -> i.getProduct().getName())
                .collect(Collectors.toList());

        customerMap.entrySet().stream()
                .filter(e -> e.getKey().equals("giorgi@gmail.com"))
                .map(Map.Entry::getValue)
                .findFirst()
                .ifPresent(c -> System.out.println("Customer: " + c.getName()));

        boolean expensive = cartItems.stream()
                .anyMatch(i -> i.getProduct().getPrice().compareTo(new BigDecimal("1000")) > 0);

        int totalQty = cartItems.stream()
                .mapToInt(OrderItem::getQuantity)
                .sum();

        Set<Product> products = allOrders.stream()
                .flatMap(o -> o.getOrderItems().stream())
                .map(OrderItem::getProduct)
                .collect(Collectors.toSet());

        List<OrderItem> sorted = cartItems.stream()
                .sorted(Comparator.comparing(i -> i.getProduct().getPrice()))
                .collect(Collectors.toList());

        runReflection();

        try (Market market = new Market("Amazon Georgia", allOrders)) {
            firstOrder.processCheckout();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void runReflection() {
        try {
            Class<?> clazz = Class.forName("product.PhysicalProduct");

            for (Field f : clazz.getDeclaredFields()) {
                System.out.println("Field: " + f.getName() + " Type: " + f.getType().getSimpleName());
            }

            for (Constructor<?> c : clazz.getConstructors()) {
                System.out.println("Constructor params: " + c.getParameterCount());
            }

            for (Method m : clazz.getDeclaredMethods()) {
                System.out.print("Method: " + m.getName());
                if (m.isAnnotationPresent(MyDeveloperInfo.class)) {
                    System.out.print(" [Annotation Present]");
                }
                System.out.println();
            }

            Constructor<?> cons = clazz.getConstructor(String.class, BigDecimal.class, Category.class, Manufacturer.class, double.class);
            Object obj = cons.newInstance("Reflected", new BigDecimal("100"), null, null, 1.0);

            Method disp = clazz.getMethod("display");
            disp.invoke(obj);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void executeOrderLogic(List<Order> orders, Predicate<Order> filter, Consumer<Order> action) {
        orders.stream().filter(filter).forEach(action);
    }

    public static void notifyCustomers(List<Order> orders, BiConsumer<Customer, String> notification) {
        orders.forEach(o -> notification.accept(o.getCustomer(), "Received"));
    }
}