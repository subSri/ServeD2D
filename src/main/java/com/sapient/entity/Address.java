package com.sapient.entity;

import lombok.Data;

@Data
public class Address {
	private Integer id;
    private Integer userId; 
    private Double lat; 
	private Double longi;
}
