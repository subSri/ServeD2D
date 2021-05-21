package com.sapient.entity;

import lombok.Data;

@Data
public class Service {

    Integer serviceId;
    Integer providerId;
    Integer addressId;
    Boolean isApproved;
    String category;
    String description;
    String imageUrl;
    Double serviceRadius;
    Double price;
    Integer ratingCount;
    Integer completedOrders;
    
}
