package com.sapient.entity;

import lombok.Data;


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
    
    
    public Integer getServiceId() {
		return serviceId;
	}

	public void setServiceId(Integer serviceId) {
		this.serviceId = serviceId;
	}

	public Integer getProviderId() {
		return providerId;
	}

	public void setProviderId(Integer providerId) {
		this.providerId = providerId;
	}

	public Integer getAddressId() {
		return addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Double getServiceRadius() {
		return serviceRadius;
	}

	public void setServiceRadius(Double serviceRadius) {
		this.serviceRadius = serviceRadius;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getRatingCount() {
		return ratingCount;
	}

	public void setRatingCount(Integer ratingCount) {
		this.ratingCount = ratingCount;
	}

	public Integer getCompletedOrders() {
		return completedOrders;
	}

	public void setCompletedOrders(Integer completedOrders) {
		this.completedOrders = completedOrders;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getRatings() {
		return ratings;
	}

	public void setRatings(Double ratings) {
		this.ratings = ratings;
	}

	private Double ratings;

    public void setRatings() {
        Double count = Double.valueOf(getRatingCount());
        Double nOrders = Double.valueOf(getCompletedOrders());
        this.ratings = count/nOrders;
    }
    
}
