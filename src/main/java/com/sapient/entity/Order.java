package com.sapient.entity;

import com.sapient.enums.Enums.*;
import java.util.Date;
import lombok.Data;

@Data
public class Order{

	private Integer orderId;
    private Integer userId;
    private Integer serviceId;
	private Integer adressId;
	private Date timestamp;
	private OrderStatus orderStatus;
	private Double amount;
	private PaymentStatus payment_status;

	public Integer getOrderStatus() {
		return orderStatus.ordinal();
	}
	public void setOrderStatus(Integer index) {
		this.orderStatus = OrderStatus.values()[index];
	}
	public Integer getPayment_status() {
		return payment_status.ordinal();
	}
	public void setPayment_status(Integer index) {
		this.payment_status = PaymentStatus.values()[index];;
	}


}