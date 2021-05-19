package com.sapient.entity;

import lombok.Data;

@Data
public class Service {

    private Integer serviceId;
    private Integer providerId;
    private Integer addressId;
    private Boolean isApproved;
    private String category;
    private String description;
    private String imageUrl;
    private Double serviceRadius;
    private Double price;
    private Integer ratingCount;
    private Integer completedOrders;
    private String name;
    private Double ratings;

    public void setRatings() {
        Double count = Double.valueOf(getRatingCount());
        Double nOrders = Double.valueOf(getCompletedOrders());
        this.ratings = count/nOrders;
    }
    
}
