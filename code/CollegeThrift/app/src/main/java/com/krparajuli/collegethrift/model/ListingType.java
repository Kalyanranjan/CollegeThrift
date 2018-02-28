package com.krparajuli.collegethrift.model;

/**
 * Created by kal on 2/24/18.
 */

public enum ListingType {
    SALE_ONLY(0),
    SALE_TRADE(1),
    TRADE_ONLY(2),
    GIVEAWAY(3);

    private final int value;

    private ListingType(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
