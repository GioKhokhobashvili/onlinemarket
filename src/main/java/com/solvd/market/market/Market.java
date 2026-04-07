package com.solvd.market.market;

import com.solvd.market.order.Order;

import java.util.HashSet;
import java.util.Set;

public class Market implements AutoCloseable {

    private String name;
    private Set<Order> orders;

    public Market(String name, Set<Order> orders) {
        this.name = name;
        this.orders = new HashSet<>(orders);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

    @Override
    public void close() throws Exception {
        System.out.println("Closed Safely");
    }
}