package com.sapient.entity;

import lombok.Data;


public class User {
	private Integer id;
	private String name;
	private String password;
	private String email;
	private String isProvider;
	private Float walletBalance;
	
	
	
	public User() {
		super();
	}

	public User(Integer id, String name, String password, String email, String isProvider, Float walletBalance) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.isProvider = isProvider;
		this.walletBalance = walletBalance;
	}
	
	public Boolean getIsProvider() {
		if (this.isProvider.equals("0")){
			return false;
		}
		else{
			return true;
		}
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Float getWalletBalance() {
		return walletBalance;
	}
	public void setWalletBalance(Float walletBalance) {
		this.walletBalance = walletBalance;
	}
	public void setIsProvider(String isProvider) {
		
		this.isProvider = isProvider;
	}
	

}