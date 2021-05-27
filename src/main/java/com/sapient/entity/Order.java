package com.sapient.entity;

import com.sapient.enums.Enums.*;
import java.sql.Date;
import lombok.Data;


public class Order{

	private Integer orderId;
    private Integer userId;
    private Integer serviceId;
	private Integer adressId;
	private Date timestamp;
	private OrderStatus orderStatus;
	private Double amount;
	
	

	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getServiceId() {
		return serviceId;
	}
	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}
	public Integer getAdressId() {
		return adressId;
	}
	public void setAdressId(Integer adressId) {
		this.adressId = adressId;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
//	public void setOrderStatus(OrderStatus orderStatus) {
//		this.orderStatus = orderStatus;
//	}
	public Integer getOrderStatus() {
		return orderStatus.ordinal();
	}
	public void setOrderStatus(Integer index) {
		this.orderStatus = OrderStatus.values()[index];
	}
}