package enums;

public enum ProductCondition {
    NEW("Factory New"),
    USED("Second Handed"),
    REFURBISHED("Fixed by factory");

    private final String info;

    ProductCondition(String info) {
        this.info = info;
    }

    public String getInfo() {
        return info;
    }
}