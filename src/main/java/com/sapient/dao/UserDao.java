package com.sapient.dao;

import java.sql.SQLException;


import com.sapient.entity.*;

public interface UserDao {
import com.sapient.entity.User;
import com.sapient.utils.DbUtil;

public class UserDao {


	public Boolean addNewUser(User user)
			throws DaoException {
		String sql = "INSERT INTO users (NAME, EMAIL, PASSWORD, ISPROVIDER, BALANCE) VALUES (?,?,?,?,?)";
		//what to do about user id generation?
		try (Connection conn = DbUtil.createConnection(); 
			PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getPassword());
			stmt.setBoolean(4, user.getIsProvider());
			stmt.setDouble(5, user.getWalletBalance());
			stmt.executeUpdate();
			System.out.println("new user added");
			return true;

		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public double getBalance(Integer userId) throws DaoException, ClassNotFoundException, SQLException {
		Double myBalance = 0.0;
		String sql = "SELECT BALANCE FROM users WHERE ID = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					myBalance = rs.getDouble("BALANCE");

				} else {
					System.out.println("No data found!");
				}

			} catch (Exception e) {
				throw new DaoException(e);
			}
			return myBalance;
		}
	}
	
	public Boolean addToWallet(Integer userId, Double amount) throws DaoException {
		Double currentBalance = 0.0;
		String sql = "SELECT * FROM users WHERE ID =  ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, userId);
			try(ResultSet rs = stmt.executeQuery();
					)
			{
				if(rs.next()) {
					currentBalance = rs.getDouble("BALANCE");
				}
				else
				{
					System.out.println("No data found!"); 
				}
			
		   }
				
		}
		catch (Exception e) {
			throw new DaoException(e);
		}
		
		String sql2 = "UPDATE users SET BALANCE = ? WHERE ID = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql2);) 
		{
			stmt.setDouble(1, amount+currentBalance);
			stmt.setInt(2,userId);
			stmt.executeUpdate(); 
			System.out.println("Wallet updated");
			return true;
		}
		catch (Exception e) 
		{
			throw new DaoException(e);
		}
		
	}
	
	public Boolean withdrawFromWallet(Integer userId, Double amount) throws DaoException
	{
		Double currentBalance = 0.0;
		String sql = "SELECT * FROM users WHERE ID =  ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, userId);
			try(ResultSet rs = stmt.executeQuery();
					)
			{
				if(rs.next()) {
					currentBalance = rs.getDouble("BALANCE");
				}
				else
				{
					System.out.println("No data found!"); 
				}
			
		   }
				
		}
		catch (Exception e) {
			throw new DaoException(e);
		}
		
		if(currentBalance>=amount)
		{
			String sql2 = "UPDATE users SET BALANCE = ? WHERE ID = ?";
			try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql2);) 
			{
				stmt.setDouble(1, currentBalance - amount);
				stmt.setInt(2,userId);
				stmt.executeUpdate(); 
				System.out.println("Wallet updated");
				return true;
			}
			catch (Exception e) 
			{
				throw new DaoException(e);
			}
			
		}
		else
		{
			System.out.println("Insufficient balance");
		}
		
		return null;
		
	}
	
	public Boolean rateService(Integer userId, Integer serviceId, String review, Integer rating) throws DaoException
	{
		String sql = "INSERT INTO review (user_id, service_id,rating,review) VALUES (?,?,?,?)";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, userId);
			stmt.setInt(2, serviceId);
			stmt.setString(3, review);
			stmt.setInt(4, rating);
			
			stmt.executeUpdate();
			System.out.println("review added");
			return true;
		}
		catch (Exception e) 
		{
			throw new DaoException(e);
		}
		
		// making changes in service table -> changes in provider rating and service rating
		
		
	}
	
	public Boolean verifyUserCreds(User user) throws DaoException;
	public Boolean addNewUser(User user) throws DaoException ;
	public Double getBalance(Integer userId) throws DaoException, ClassNotFoundException, SQLException ;
	public Boolean addToWallet(Integer userId, Double amount) throws DaoException ;
	public Boolean withdrawFromWallet(Integer userId, Double amount) throws DaoException;
	public User getUserInfo(Integer userId) throws DaoException;

}
