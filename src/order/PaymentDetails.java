package order;

public class PaymentDetails {

    private String paymentMethod;
    private String transactionStatus;

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public PaymentDetails(String paymentMethod, String transactionStatus) {
        this.paymentMethod = paymentMethod;
        this.transactionStatus = transactionStatus;
    }
}
