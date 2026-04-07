package com.solvd.market.interfaces;

import com.solvd.market.order.OrderItem;

@FunctionalInterface
public interface ItemValidator {

    boolean validate(OrderItem item);
}