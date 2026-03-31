package enums;

public enum DiscountTier {
    NONE(0), SILVER(5), GOLD(10), PLATINUM(20);

    private final int discountPercent;

    DiscountTier(int discountPercent) {
        this.discountPercent = discountPercent;
    }

    public int getDiscountPercent() {
        return discountPercent;
    }
}