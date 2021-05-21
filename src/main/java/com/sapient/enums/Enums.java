package com.sapient.enums;

public final class Enums {
    
    public enum OrderStatus {
        REQUESTED,
        CONFIRMED,
        COMPLETED,
        CANCELLED,
        REJECTED
    }

    public enum PaymentStatus {
        CONFIRMED,
        PENDING
    }

    // public enum PaymentMode {
    //     CASH,
    //     CARD,
    //     UPI
    // }
}
