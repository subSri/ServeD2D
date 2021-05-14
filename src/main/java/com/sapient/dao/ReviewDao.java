package com.sapient.dao;

import java.util.List;

import com.sapient.entity.Review;

public interface ReviewDao {
	 public Review addReview(Review review)throws DaoException;
	 public List<Review> returnAllReviews() throws DaoException ;
	 public List<Review> returnSelectedReviews(Integer serviceId) throws DaoException;
	 public Boolean updateRatingCount(Integer rating, Integer serviceId) throws DaoException;
	 

}
