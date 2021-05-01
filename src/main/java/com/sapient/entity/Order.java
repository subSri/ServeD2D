package com.sapient.entity;

import com.sapient.enums.Enums.*;

import java.util.Date;
import lombok.Data;

@Data
public class Order{


    private Integer orderId;
    private Integer userId;
    private Integer serviceId;
	private Date timestamp;
	private OrderStatus order_status;
	private PaymentMode payment_mode;
	private PaymentStatus payment_status;
}