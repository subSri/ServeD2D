package com.sapient.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import com.sapient.entity.Review;
import com.sapient.utils.DbUtil;

public class ReviewDaoImpl implements ReviewDao {
    
    public Review addReview(Review review)
    throws DaoException {
    String sql = "INSERT INTO REVIEW (review_id, user_id, service_id, rating, comment) VALUES (?,?,?,?,?)";
    //what to do about user id generation?
    try (Connection conn = DbUtil.createConnection(); 
        PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
        stmt.setInt(1, review.getReviewId());
        stmt.setInt(2, review.getUserId());
        stmt.setInt(3, review.getServiceId());
        stmt.setInt(4, review.getRating());
        stmt.setString(5,review.getComment());

        stmt.executeUpdate();
        System.out.println("review added");
        updateRatingCount(review.getRating(), review.getServiceId());
        return review;
        

    } catch (Exception e) {
        throw new DaoException(e);
    }
    
}

    public List<Review> returnAllReviews() throws DaoException {
    
        List<Review> reviews = new ArrayList<Review>();
        
        String sql = "SELECT * FROM REVIEW ";
        try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
        {
            try(ResultSet rs = stmt.executeQuery();)
            {
                if(rs.next()) {
                    do {
                        Review review = new Review();
                        review.setUserId(rs.getInt("user_id"));
                        review.setServiceId(rs.getInt("service_id"));
                        review.setRating(rs.getInt("rating"));
                        review.setComment(rs.getString("comment"));
                        reviews.add(review);
                    } while (rs.next());

                }
                else
                {
                    System.out.println("No data found!"); 
                }
            
            }
            catch (Exception e) {
                throw new DaoException(e);
            }
                
        }
        catch (Exception e) {
            throw new DaoException(e);
        }
        return reviews;


}

    public List<Review> returnSelectedReviews(Integer serviceId) throws DaoException {
		
		List<Review> reviews = new ArrayList<Review>();
		
		String sql = "SELECT * FROM REVIEW WHERE service_id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
            stmt.setInt(1,serviceId);
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
                    do{
                        Review review = new Review();
						review.setUserId(rs.getInt("user_id"));
						review.setServiceId(rs.getInt("service_id"));
						review.setRating(rs.getInt("rating"));
						review.setComment(rs.getString("comment"));
						review.setReviewId(rs.getInt("review_id"));
                        reviews.add(review);
                    }while(rs.next());
				}
				else
				{
					System.out.println("No Reviews on given Id"); 
				}
			
			}
			catch (Exception e) {
				throw new DaoException(e);
			}
				
		}
		catch (Exception e) {
			throw new DaoException(e);
		}
		return reviews;

	}
  
	public Boolean updateRatingCount(Integer rating, Integer serviceId) throws DaoException {
        //how to authenticate the provider here
        String sql = "UPDATE SERVICE SET rating_count = rating_count + ? WHERE service_id = ?";
        //what to do about user id generation?
        try (Connection conn = DbUtil.createConnection(); 
            PreparedStatement stmt = conn.prepareStatement(sql);
            ) {
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