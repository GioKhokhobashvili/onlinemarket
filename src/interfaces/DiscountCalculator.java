package interfaces;

import java.math.BigDecimal;

@FunctionalInterface
public interface DiscountCalculator {

    BigDecimal applyDiscount(BigDecimal price, double percentage);
}