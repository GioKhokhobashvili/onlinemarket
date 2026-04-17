package com.solvd.market.interfaces;

import com.solvd.market.order.Order;

@FunctionalInterface
public interface OrderAction {

    void perform(Order order);

}