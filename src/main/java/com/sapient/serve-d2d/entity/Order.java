package com.sapient.entity;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Order{

    enum Order_Status {
        CONFIRMED,
        ON_THE_WAY,
        COMPLETED,
        CANCELLED
    }

    enum Payment_Status {
        CONFIRMED,
        FAILED
    }

    enum Payment_Mode {
        CASH,
        CARD,
        UPI
    }

    private Integer order_id;
    private Integer user_id;
    private Integer service_id;
	private LocalDateTime timestamp;
	private Order_Status order_status;
	private Payment_Mode payment_mode;
	private Payment_Status payment_status;
}