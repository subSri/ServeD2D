package com.sapient.dao;

import java.sql.*;

import com.sapient.entity.User;
import com.sapient.dto.LoginUser;
import com.sapient.utils.DbUtil;

public class UserDaoImpl implements UserDao {

	public Boolean verifyUserCreds(LoginUser user) throws DaoException {
		String sql = "SELECT password FROM USER where email = ?";
		try (Connection conn = DbUtil.createConnection(); 
			PreparedStatement stmt = conn.prepareStatement(sql);
			) {

			stmt.setString(1, user.getEmail());

			try (ResultSet rs = stmt.executeQuery();) {
				if (rs.next()) {
					String password = rs.getString("password");
					// System.out.println("Pass " + user.getPassword()+" "+password);
					if (password.equals(user.getPassword())){
						System.out.println("User Verified");
						return true;
					}
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
//		String sql = "INSERT INTO USER (user_id, name, email, password, is_provider, wallet_balance) VALUES (?,?,?,?,?,?)";
		String sql = "INSERT INTO USER (name, email, password, is_provider, wallet_balance) VALUES (?,?,?,?,?)";
		try (Connection conn = DbUtil.createConnection(); 
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			) {
//			stmt.setInt(1, user.getId());
			stmt.setString(1, user.getName());
			stmt.setString(2, user.getEmail());
			stmt.setString(3, user.getPassword());
			stmt.setBoolean(4, user.getIsProvider());
			stmt.setDouble(5, user.getWalletBalance());

			stmt.executeUpdate();
			
			ResultSet key = stmt.getGeneratedKeys();
		    if(key.next())
			{
				int id = key.getInt(1);  // or "review_id"
				user.setId(id);
				System.out.println("new user added" + id);
			}
			return true;

		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public Double getBalance(Integer userId) throws DaoException, ClassNotFoundException, SQLException {
		Double myBalance = 0.0;
		String sql = "SELECT wallet_balance FROM USER WHERE user_id = ?";
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
	
		String sql2 = "UPDATE USER SET wallet_balance = wallet_balance + ? WHERE user_id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql2);) 
		{
			stmt.setDouble(1, amount);
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
		String sql = "SELECT * FROM USER WHERE user_id =  ?";
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
			String sql2 = "UPDATE USER SET wallet_balance = ? WHERE user_id = ?";
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
	
	public User getUserInfo(Integer userId) throws DaoException
	{
		String sql = "SELECT * FROM USER WHERE user_id=?";
		//what to do about user user_id generation?
		User user = new User();
		try (Connection conn = DbUtil.createConnection(); 
			PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, userId);
			try(ResultSet rs = stmt.executeQuery();
					)
			{
				if(rs.next()) {
					
					user.setId(rs.getInt("user_id"));
					user.setName(rs.getString("name"));
					user.setEmail(rs.getString("email"));
					user.setPassword(rs.getString("password"));
					user.setIsProvider(rs.getString("is_provider"));
//					user.setWalletBalance(rs.getFloat(5));
				}
				else
				{
					System.out.println("No data found!"); 
				}
			
		   }

			System.out.println("new user added");
			return user;

		} catch (Exception e) {
			throw new DaoException(e);
		}
		
	}
	
	public User getUser(String email) throws DaoException
	{
		String sql = "SELECT * FROM USER WHERE email=?";
		//what to do about user user_id generation?
		User user = new User();
		try (Connection conn = DbUtil.createConnection(); 
			PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setString(1, email);
			try(ResultSet rs = stmt.executeQuery();
					)
			{
				if(rs.next()) {
					
					user.setId(rs.getInt("user_id"));
					user.setName(rs.getString("name"));
					user.setEmail(rs.getString("email"));
					user.setPassword(rs.getString("password"));
					user.setIsProvider(rs.getString("is_provider"));
//					user.setWalletBalance(rs.getFloat(5));
				}
				else
				{
					System.out.println("No data found!");
					return null;
				}
			
		   }

			System.out.println("new user added");
			return user;

		} catch (Exception e) {
			throw new DaoException(e);
		}
		
	}
	
}

