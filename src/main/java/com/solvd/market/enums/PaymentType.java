package com.solvd.market.enums;

import java.math.BigDecimal;

public enum PaymentType {
    CASH(0.0),
    CREDIT_CARD(0.02),
    CRYPTO(0.05);

    private final double transactionFee;

    PaymentType(double transactionFee) {
        this.transactionFee = transactionFee;
    }

    public BigDecimal calculateFinalAmount(BigDecimal amount) {
        BigDecimal fee = amount.multiply(BigDecimal.valueOf(transactionFee));
        return amount.add(fee);
    }
}