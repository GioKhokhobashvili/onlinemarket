package com.solvd.market.order;

import com.solvd.market.enums.PaymentType;
import com.solvd.market.interfaces.Payable;

public class PaymentDetails extends OrderDetail implements Payable {

    private String paymentMethod;
    private String transactionStatus;
    private PaymentType paymentType;

    public PaymentDetails(int id, String paymentMethod, String transactionStatus, PaymentType paymentType) {
        super(id, "Payment");
        this.paymentMethod = paymentMethod;
        this.transactionStatus = transactionStatus;
        this.paymentType = paymentType;
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

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    @Override
    public String getPaymentInfo() {
        return paymentMethod + " - " + transactionStatus;
    }
}