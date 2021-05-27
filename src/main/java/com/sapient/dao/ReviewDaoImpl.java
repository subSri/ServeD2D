package com.sapient.dao;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sapient.entity.Review;
import com.sapient.utils.DbUtil;

public class ReviewDaoImpl implements ReviewDao {

    public Review addReview(Review review) throws DaoException {
        // String sql = "INSERT INTO REVIEW (review_id, user_id, service_id, rating,
        // comment) VALUES (?,?,?,?,?)";
        String sql = "INSERT INTO REVIEW (user_id, service_id, rating, comment) VALUES (?,?,?,?)";
        try (Connection conn = DbUtil.createConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            // stmt.setInt(1, review.getReviewId());
            stmt.setInt(1, review.getUserId());
            stmt.setInt(2, review.getServiceId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());

            stmt.executeUpdate();

            ResultSet key = stmt.getGeneratedKeys();
            if (key.next()) {
                int id = key.getInt(1); // or "review_id"
                System.out.println("review added" + id);
            }
            updateRatingCount(review.getRating(), review.getServiceId());
            return review;

        } catch (Exception e) {
            throw new DaoException(e);
        }

    }

    public List<Review> returnAllReviews() throws DaoException {

        List<Review> reviews = new ArrayList<Review>();

        String sql = "SELECT * FROM REVIEW ";
        try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    do {
                        Review review = getReviewObj(rs);
                        reviews.add(review);
                    } while (rs.next());

                } else {
                    System.out.println("No data found!");
                }

            } catch (Exception e) {
                throw new DaoException(e);
            }

        } catch (Exception e) {
            throw new DaoException(e);
        }
        return reviews;

    }

    public List<Map<String,String>> returnSelectedReviews(Integer serviceId) throws DaoException {

        List<Map<String,String>> reviews = new ArrayList<Map<String,String>>();

        String sql = "SELECT r.review_id,r.user_id,r.service_id,r.rating,r.comment,u.name FROM REVIEW r, USER u  WHERE r.service_id = ? and r.user_id=u.user_id ";
        try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, serviceId);
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    do {
                        Map<String,String> review= new HashMap<>();
                        review.put("review_id",rs.getString("review_id"));
                        review.put("user_id",rs.getString("user_id"));
                        review.put("service_id",rs.getString("service_id"));
                        review.put("rating",rs.getString("rating"));
                        review.put("comment",rs.getString("comment"));
                        review.put("name",rs.getString("name"));
                        reviews.add(review);
                    } while (rs.next());
                } else {
                    System.out.println("No Reviews on given Id");
                }

            } catch (Exception e) {
                throw new DaoException(e);
            }

        } catch (Exception e) {
            throw new DaoException(e);
        }
        return reviews;

    }

    private Review getReviewObj(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setUserId(rs.getInt("user_id"));
        review.setServiceId(rs.getInt("service_id"));
        review.setRating(rs.getInt("rating"));
        review.setComment(rs.getString("comment"));
        review.setReviewId(rs.getInt("review_id"));
        return review;
    }

    public Boolean updateRatingCount(Integer rating, Integer serviceId) throws DaoException {
        // how to authenticate the provider here
        String sql = "UPDATE SERVICE SET rating_count = rating_count + ? WHERE service_id = ?";
        // what to do about user id generation?
        try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, rating);
            stmt.setInt(2, serviceId);

            stmt.executeUpdate();
            System.out.println("rating count updated in service");
            return true;

        } catch (Exception e) {
            throw new DaoException(e);
        }
    }

}