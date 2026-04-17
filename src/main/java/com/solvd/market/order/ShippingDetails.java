package com.solvd.market.order;

import com.solvd.market.enums.ShippingMethod;
import com.solvd.market.interfaces.Shippable;

public class ShippingDetails extends OrderDetail implements Shippable {

    private String courierName;
    private String trackingNumber;
    private ShippingMethod shippingMethod;

    public ShippingDetails(int id, String courierName, String trackingNumber, ShippingMethod shippingMethod) {
        super(id, "Shipping");
        this.courierName = courierName;
        this.trackingNumber = trackingNumber;
        this.shippingMethod = shippingMethod;
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

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    @Override
    public String getShippingInfo() {
        return courierName + " - " + trackingNumber;
    }
}