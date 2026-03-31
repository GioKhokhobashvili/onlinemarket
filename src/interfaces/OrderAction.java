package interfaces;

import order.Order;

@FunctionalInterface
public interface OrderAction {

    void perform(Order order);
}