package com.solvd.market.order;

import com.solvd.market.interfaces.Identifiable;

public class BaseEntity implements Identifiable {

    private int id;

    public BaseEntity(int id) {
        this.id = id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }
}