package interfaces;

import order.OrderItem;

@FunctionalInterface
public interface ItemValidator {

    boolean validate(OrderItem item);
}