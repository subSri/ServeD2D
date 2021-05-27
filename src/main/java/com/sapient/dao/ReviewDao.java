package com.sapient.dao;

import java.util.List;
import java.util.Map;

import com.sapient.entity.Review;

public interface ReviewDao {
	 public Review addReview(Review review)throws DaoException;
	 public List<Review> returnAllReviews() throws DaoException ;
	 public List<Map<String, String>> returnSelectedReviews(Integer serviceId) throws DaoException;
	 public Boolean updateRatingCount(Integer rating, Integer serviceId) throws DaoException;
	 

}
