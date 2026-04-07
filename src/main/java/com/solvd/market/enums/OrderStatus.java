package com.solvd.market.enums;

public enum OrderStatus {
    PENDING("Waiting for payment"),
    PROCESSING("Ready to be send"),
    SHIPPED("On the way"),
    DELIVERED("Your order is delivered"),
    CANCELLED("Your order is canceled ");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFinalState() {
        return this == DELIVERED || this == CANCELLED;
    }
}