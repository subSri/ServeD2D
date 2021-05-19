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

    public Integer getServiceId() {
        return serviceId;
    }
    public Double getRatings() {
        return ratings;
    }
    public String getName() {
        return name;
    }
    public Integer getCompletedOrders() {
        return completedOrders;
    } 
    public Integer getRatingCount() {
        return ratingCount;
    } 
    public Double getPrice() {
        return price;
    }  
    public Double getServiceRadius() {
        return serviceRadius;
    }  
    public String getImageUrl() {
        return imageUrl;
    }  
    public String getDescription() {
        return description;
    } 
    public String getCategory() {
        return category;
    }
    public Boolean getIsApproved() {
        return isApproved;
    } 
    public Integer getAddressId() {
        return addressId;
    }
    public Integer getProviderId() {
        return providerId;
    }
    public void setRatings() {
        Double count = Double.valueOf(getRatingCount());
        Double nOrders = Double.valueOf(getCompletedOrders());
        this.ratings = count/nOrders;
    }
    public void setProviderId(Integer providerId) {
        this.providerId = providerId;
    }
    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }
    public void setCompletedOrders(Integer completedOrders) {
        this.completedOrders = completedOrders;
    }
    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public void setServiceRadius(Double serviceRadius) {
        this.serviceRadius = serviceRadius;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }
    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
    public void setName(String name) {
        this.name = name;
    }
    
}
