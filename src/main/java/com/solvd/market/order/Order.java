package com.solvd.market.order;

import com.solvd.market.customer.Customer;
import com.solvd.market.enums.OrderStatus;
import com.solvd.market.exceptions.EmptyCartException;
import com.solvd.market.exceptions.PaymentException;
import com.solvd.market.interfaces.Payable;
import com.solvd.market.interfaces.Shippable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class Order {

    public static int totalOrdersPlaced = 0;

    private Shippable shippingDetails;
    private Payable paymentDetails;
    private List<OrderItem> orderItems;
    private Customer customer;
    private LocalDateTime localDateTime;
    private OrderStatus orderStatus;

    public Order(LocalDateTime localDateTime, Customer customer, List<OrderItem> orderItems,
                 Shippable shippingDetails, Payable paymentDetails, OrderStatus orderStatus) {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new EmptyCartException("You cannot create an order with zero items");
        }
        this.localDateTime = localDateTime;
        this.customer = customer;
        this.orderItems = orderItems;
        this.shippingDetails = shippingDetails;
        this.paymentDetails = paymentDetails;
        this.orderStatus = orderStatus;
        totalOrdersPlaced++;
    }

    public void processCheckout() throws PaymentException {
        if (paymentDetails == null) {
            throw new PaymentException("No payment details are provided");
        }
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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            throw new EmptyCartException("Order items cannot be empty");
        }
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

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(customer, order.customer) &&
                Objects.equals(localDateTime, order.localDateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customer, localDateTime);
    }
}