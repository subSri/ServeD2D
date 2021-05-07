package com.sapient.entity;

import lombok.Data;

@Data
public class User{
    private Integer id;
	private String name;
	private String email;
	private Boolean isProvider;
	private Float walletBalance;

}