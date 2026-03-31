package enums;

public enum ShippingMethod {
    STANDARD(5.0, 5),
    EXPRESS(15.0, 1),
    PICKUP(0.0, 0);

    private final double cost;
    private final int estimatedDays;

    ShippingMethod(double cost, int estimatedDays) {
        this.cost = cost;
        this.estimatedDays = estimatedDays;
    }

    public double getCost() { return cost; }
    public int getEstimatedDays() { return estimatedDays; }
}