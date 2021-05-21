package com.sapient.entity;

import lombok.Data;

@Data
public class User {
	private Integer id;
	private String name;
	private String password;
	private String email;
	private String isProvider;

	public Boolean getIsProvider() {
		if (this.isProvider == "0"){
			return false;
		}
		else{
			return true;
		}
	}
	public void setIsProvider(String isProvider) {
		
		this.isProvider = isProvider;
	}
	private Float walletBalance;

}