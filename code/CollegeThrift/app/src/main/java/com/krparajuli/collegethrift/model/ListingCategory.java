package com.krparajuli.collegethrift.model;

/**
 * Created by kal on 2/24/18.
 */

public enum ListingCategory {
    BOOK(0),
    STATIONERY(1),
    GADGET(2),
    APPLIANCE(3),
    CLOTHING(4),
    DECORATIVE(5),
    KITCHEN(6),
    OTHER(7);

    private final int value;

    private ListingCategory(int value) {
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
