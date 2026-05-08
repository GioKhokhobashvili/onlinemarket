package com.solvd.market.pool;

public class Connection {

    private final int id;

    public Connection(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void execute(String query) {
        System.out.println("Connection-" + id + " executing: " + query);
    }
}