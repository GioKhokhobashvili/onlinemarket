package order;

import interfaces.Payable;

public class PaymentDetails extends OrderDetail implements Payable {

    private String paymentMethod;
    private String transactionStatus;

    public PaymentDetails(int id, String paymentMethod, String transactionStatus) {
        super(id, "Payment");
        this.paymentMethod = paymentMethod;
        this.transactionStatus = transactionStatus;
    }

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

    @Override
    public String getPaymentInfo() {
        return paymentMethod + " - " + transactionStatus;
    }
}