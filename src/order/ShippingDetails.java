package order;

import interfaces.Shippable;

public class ShippingDetails extends OrderDetail implements Shippable {

    private String courierName;
    private String trackingNumber;

    public ShippingDetails(int id, String courierName, String trackingNumber) {
        super(id, "Shipping");
        this.courierName = courierName;
        this.trackingNumber = trackingNumber;
    }


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

    @Override
    public String getShippingInfo() {
        return courierName + " - " + trackingNumber;
    }
}