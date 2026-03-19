package order;

import customer.Customer;
import java.time.LocalDateTime;
import interfaces.Shippable;
import interfaces.Payable;

public class Order {

    public static int totalOrdersPlaced = 0;

    private Shippable shippingDetails;
    private Payable paymentDetails;
    private OrderItem[] orderItems;
    private Customer customer;
    private LocalDateTime localDateTime;

    public Order(LocalDateTime localDateTime, Customer customer, OrderItem[] orderItems,
                 Shippable shippingDetails, Payable paymentDetails) {
        this.localDateTime = localDateTime;
        this.customer = customer;
        this.orderItems = orderItems;
        this.shippingDetails = shippingDetails;
        this.paymentDetails = paymentDetails;
        totalOrdersPlaced++;
    }

    public static int getTotalOrdersPlaced() {
        return totalOrdersPlaced;
    }

    public static void setTotalOrdersPlaced(int totalOrdersPlaced) {
        Order.totalOrdersPlaced = totalOrdersPlaced;
    }

    public Shippable getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(Shippable shippingDetails) {
        this.shippingDetails = shippingDetails;
    }

    public Payable getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(Payable paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    public OrderItem[] getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItem[] orderItems) {
        this.orderItems = orderItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }
}