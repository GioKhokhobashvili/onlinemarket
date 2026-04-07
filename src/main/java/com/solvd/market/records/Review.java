package com.solvd.market.records;

public record Review(String customerName, int rating, String comment) {

    public Review {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating needs to be between 1 and 5");
        }
    }
}