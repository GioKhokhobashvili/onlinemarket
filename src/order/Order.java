package order;

import customer.Customer;
import java.time.LocalDateTime;
import interfaces.Shippable;
import interfaces.Payable;
import exceptions.EmptyCartException;
import exceptions.PaymentException;

public class Order {

    public static int totalOrdersPlaced = 0;

    private Shippable shippingDetails;
    private Payable paymentDetails;
    private OrderItem[] orderItems;
    private Customer customer;
    private LocalDateTime localDateTime;

    public Order(LocalDateTime localDateTime, Customer customer, OrderItem[] orderItems,
                 Shippable shippingDetails, Payable paymentDetails) {
        if (orderItems == null || orderItems.length == 0) {
            throw new EmptyCartException("You cannot create an order with zero items");
        }
        this.localDateTime = localDateTime;
        this.customer = customer;
        this.orderItems = orderItems;
        this.shippingDetails = shippingDetails;
        this.paymentDetails = paymentDetails;
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

    public OrderItem[] getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(OrderItem[] orderItems) {
        if (orderItems == null || orderItems.length == 0) {
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
}