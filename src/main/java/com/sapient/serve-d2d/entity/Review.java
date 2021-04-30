package com.sapient.entity;

public class Review {
	private int user_id;
    private int service_id;
    private int provider_id;
    private int rating; 
    private String comment; 
    
    
    public int getProvider_id() {
		return provider_id;
	}
	public void setProvider_id(int provider_id) {
		this.provider_id = provider_id;
	}
     public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getService_id() {
		return service_id;
	}
	public void setService_id(int service_id) {
		this.service_id = service_id;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
