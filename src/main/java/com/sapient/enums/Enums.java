package com.sapient.enums;

public final class Enums {
    
    public enum OrderStatus {
        CONFIRMED,
        ON_THE_WAY,
        COMPLETED,
        CANCELLED
    }

    public enum PaymentStatus {
        CONFIRMED,
        FAILED
    }

    public enum PaymentMode {
        CASH,
        CARD,
        UPI
    }
}
