package com.sapient.entity;

import lombok.Data;

@Data
public class User{
    private Integer id;
	private String name;
	private String email;
	private boolean isProvider;
	private String location;
}