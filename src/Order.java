import java.time.LocalDateTime;

public class Order {
    private ShippingDetails shippingDetails;
    private PaymentDetails paymentDetails;
    private OrderItem[] orderItems;
    private Customer customer;
    private LocalDateTime localDateTime;

    public ShippingDetails getShippingDetails() {
        return shippingDetails;
    }

    public void setShippingDetails(ShippingDetails shippingDetails) {
        this.shippingDetails = shippingDetails;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
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

    public Order(LocalDateTime localDateTime, Customer customer, OrderItem[] orderItems, ShippingDetails shippingDetails, PaymentDetails paymentDetails) {
        this.localDateTime = localDateTime;
        this.customer = customer;
        this.orderItems = orderItems;
        this.shippingDetails = shippingDetails;
        this.paymentDetails = paymentDetails;
    }
    }
