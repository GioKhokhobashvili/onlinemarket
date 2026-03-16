package market;

import order.Order;

public class Market {

    private String name;
    private Order[] orders;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Order[] getOrders() {
        return orders;
    }

    public void setOrders(Order[] orders) {
        this.orders = orders;
    }

    public Market(String name, Order[] orders) {
        this.name = name;
        this.orders = orders;

    }
}

