package com.sapient.entity;

import com.sapient.enums.Enums.*;
import java.sql.Date;
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

	public Integer getOrderStatus() {
		return orderStatus.ordinal();
	}
	public void setOrderStatus(Integer index) {
		this.orderStatus = OrderStatus.values()[index];
	}
}