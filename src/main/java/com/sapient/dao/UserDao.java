package com.sapient.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.sapient.entity.Address;
import com.sapient.entity.Service;
import com.sapient.entity.User;
import com.sapient.utils.DbUtil;

public class UserDao {

	public Boolean verifyUserCreds(User user) throws DaoException {
		String sql = "SELECT password FROM users where id = ?";
		//what to do about user id generation?
		try (Connection conn = DbUtil.createConnection(); 
			PreparedStatement stmt = conn.prepareStatement(sql);
			) {

			stmt.setString(1, user.getEmail());
			stmt.executeUpdate();

			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					String password = rs.getString("password");
					if (user.getPassword() == password){
						System.out.println("User Verified");
						return true;
					}
				} else {
					System.out.println("No such user");
				}

			} catch (Exception e) {
				throw new DaoException(e);
			}

			
			return false;

		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public Boolean addNewUser(User user) throws DaoException {
		String sql = "INSERT INTO users (ID, NAME, EMAIL, PASSWORD, is_provider, wallet_balance) VALUES (?,?,?,?,?,?)";
		//what to do about user id generation?
		try (Connection conn = DbUtil.createConnection(); 
			PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, user.getId());
			stmt.setString(2, user.getName());
			stmt.setString(3, user.getEmail());
			stmt.setString(4, user.getPassword());
			stmt.setBoolean(5, user.getIsProvider());
			stmt.setDouble(6, user.getWalletBalance());

			stmt.executeUpdate();
			System.out.println("new user added");
			return true;

		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public Double getBalance(Integer userId) throws DaoException, ClassNotFoundException, SQLException {
		Double myBalance = 0.0;
		String sql = "SELECT wallet_balance FROM users WHERE ID = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, userId);
			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					myBalance = rs.getDouble("wallet_balance");

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
					currentBalance = rs.getDouble("wallet_balance");
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
		
		String sql2 = "UPDATE users SET wallet_balance = ? WHERE ID = ?";
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
	
	public Boolean withdrawFromWallet(Integer userId, Double amount) throws DaoException {
		Double currentBalance = 0.0;
		String sql = "SELECT * FROM users WHERE ID =  ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, userId);
			try(ResultSet rs = stmt.executeQuery();
					)
			{
				if(rs.next()) {
					currentBalance = rs.getDouble("wallet_balance");
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
			String sql2 = "UPDATE users SET wallet_balance = ? WHERE ID = ?";
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
	
	public List<Service> getTopRatedNServicesWithinACategory(String category, Integer n) throws DaoException
	{
		String sql = "SELECT * FROM user WHERE id IN (SELECT provider_id FROM service WHERE category = ? ORDER BY (rating/completed_orders) DESC LIMIT ?)";
		List<Service> topServices = new ArrayList<Service>();
		try(
		Connection conn = DbUtil.createConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		){
			stmt.setString(1, category);
			stmt.setInt(2, n);
			try(ResultSet rs = stmt.executeQuery();) {
				while(rs.next()) {
					Service service = new Service();
					service.setServiceId(rs.getInt("service_id"));
					service.setProviderId(rs.getInt("provider_id"));
					service.setAddressId(rs.getInt("address_id"));
					service.setIsApproved(rs.getBoolean("is_approved"));
					service.setCategory(rs.getString("category"));
					service.setDescription(rs.getString("description"));
					service.setImageUrl(rs.getString("image_url"));
					service.setServiceRadius(rs.getDouble("service_radius"));
					service.setPrice(rs.getDouble("price"));
					service.setRatingCount(rs.getInt("rating_count"));
					service.setCompletedOrders(rs.getInt("completed_orders"));
					topServices.add(service);
				}
			}
			catch (Exception e) 
			{
				throw new DaoException(e);
			}
		}
		catch (Exception e) 
		{
			throw new DaoException(e);
		}
		return topServices;
	}
	
	public List<Service> findNearByServicesSortedByDistance(String category, Integer n, Address userAddress) throws DaoException {

		String sql = "SELECT *,(3959 * acos (cos ( radians(?) )* cos( radians( address.lat ) )* cos( radians( address.longi ) - radians(?) )+ sin ( radians(?) )* sin( radians( address.lat ) )))"+
			"AS distance FROM users,address where users.id = address.user_id AND users.id IN (SELECT provider_id FROM service WHERE category = ? ) ORDER BY distance DESC LIMIT ?";
		List<Service> topServiceProviders = new ArrayList<Service>();
		try(
		Connection conn = DbUtil.createConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		){
			
			
			stmt.setDouble(1, userAddress.getLat());
			stmt.setDouble(2, userAddress.getLongi());
			stmt.setDouble(3, userAddress.getLat());
			stmt.setString(4, category);
			stmt.setInt(5, n);
			try(ResultSet rs = stmt.executeQuery();) {
				while(rs.next()) {
					Service service = new Service();
					service.setServiceId(rs.getInt("service_id"));
					service.setProviderId(rs.getInt("provider_id"));
					service.setAddressId(rs.getInt("address_id"));
					service.setIsApproved(rs.getBoolean("is_approved"));
					service.setCategory(rs.getString("category"));
					service.setDescription(rs.getString("description"));
					service.setImageUrl(rs.getString("image_url"));
					service.setServiceRadius(rs.getDouble("service_radius"));
					service.setPrice(rs.getDouble("price"));
					service.setRatingCount(rs.getInt("rating_count"));
					service.setCompletedOrders(rs.getInt("completed_orders"));
					topServiceProviders.add(service);
				}
			}
			catch (Exception e) 
			{
				throw new DaoException(e);
			}
		}
		catch (Exception e) 
		{
			throw new DaoException(e);
		}
		return topServiceProviders;
	}
	}
  
  
 
	
	
	
	
	
	
	// for testing purpose 
//	public static void main(String[] args) throws DaoException {
//		String name = KeyboardUtil.getString("Enter name:");
//		String email = KeyboardUtil.getString("Enter email:");
//		String password = KeyboardUtil.getString("Enter password:");
//		double balance = KeyboardUtil.getDouble("Enter amount to add in wallet:");
//		Integer isProvider = KeyboardUtil.getInt("are you a provider?[0/1] : ");
//		
//		User newUser = new User();
//		newUser.setName(name);
//		newUser.setEmail(email);
//		newUser.setPassword(password);
//		newUser.setBalance(balance);
//		newUser.setIsProvider(isProvider);
//		
//		UserDao userDao = new UserDao();
//		if(userDao.addNewUser(newUser)) {
//			System.out.println("new user added --> inside main()");
//		}
//	}
//	
	
