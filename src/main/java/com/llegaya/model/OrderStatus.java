package com.llegaya.model;

public enum OrderStatus {
    PENDING,
    ACCEPTED,
    IN_TRANSIT,
    DELIVERED,
    CANCELLED;

    public static OrderStatus getACCEPTED() {
        return ACCEPTED;
    }
}
