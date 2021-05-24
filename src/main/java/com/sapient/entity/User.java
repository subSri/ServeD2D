package com.sapient.entity;

import lombok.Data;

@Data
public class User {
	private Integer id;
	private String name;
	private String password;
	private String email;
	private String isProvider;
	private Float walletBalance;
	public Boolean getIsProvider() {
		if (this.isProvider.equals("0")){
			return false;
		}
		else{
			return true;
		}
	}
	public void setIsProvider(String isProvider) {
		
		this.isProvider = isProvider;
	}
	

}