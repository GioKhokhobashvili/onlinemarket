public class ShippingDetails {
    private String courierName;
    private String trackingNumber;

    public String getCourierName() {
        return courierName;
    }

    public void setCourierName(String courierName) {
        this.courierName = courierName;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public ShippingDetails(String courierName, String trackingNumber) {
        this.courierName = courierName;
        this.trackingNumber = trackingNumber;
    }
}
