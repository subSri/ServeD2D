package com.sapient.entity;

import lombok.Data;

@Data
public class Review {
    private Integer reviewId;
	private Integer userId;
    private Integer serviceId;
    private Integer rating; 
    private String comment; 
    
	
}
