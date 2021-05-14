package com.sapient.dao;

import com.sapient.enums.Enums.OrderStatus;
import com.sapient.utils.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.List;

import com.sapient.entity.*;


public class OrderDaoImpl implements OrderDao {
    
    public Boolean addNewOrder(Order order)
			throws DaoException {
		String sql = "INSERT INTO ORDER (order_id, user_id, service_id, address_id, timestamp, status, amount) VALUES (?,?,?,?,?,?,?)";
		//what to do about user id generation?
		try (Connection conn = DbUtil.createConnection(); 
			PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, order.getOrderId());
            stmt.setInt(2, order.getUserId());
            stmt.setInt(3, order.getServiceId());
            stmt.setInt(4, order.getAdressId());
			stmt.setDate(5, order.getTimestamp());
			stmt.setInt(6, order.getOrderStatus());
			stmt.setDouble(7, order.getAmount());

			stmt.executeUpdate();
			System.out.println("new order added");
			return true;

		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public List<Order> returnAllOrders(Integer userId) throws DaoException {
		
		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM ORDER WHERE user_id =  ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, userId);
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					do {
						Order order = new Order();
						order.setOrderId(rs.getInt("order_id"));
						order.setUserId(rs.getInt("user_id"));
						order.setServiceId(rs.getInt("service_id"));
						order.setAdressId(rs.getInt("address_id"));
						order.setAmount(rs.getDouble("amount"));
						order.setTimestamp(rs.getDate("timestamp"));
						order.setOrderStatus(rs.getInt("status"));
						orders.add(order);
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
		return orders;

	}

	public List<Order> returnAllIncompleteOrders(Integer userId) throws DaoException {
		
		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM ORDER WHERE user_id =  ? AND STATUS = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, userId);
			stmt.setInt(2, OrderStatus.CONFIRMED.ordinal());
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					do {
						Order order = new Order();
						order.setOrderId(rs.getInt("order_id"));
						order.setUserId(rs.getInt("user_id"));
						order.setServiceId(rs.getInt("service_id"));
						order.setAdressId(rs.getInt("address_id"));
						order.setAmount(rs.getDouble("amount"));
						order.setTimestamp(rs.getDate("timestamp"));
						order.setOrderStatus(rs.getInt("status"));
						orders.add(order);
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
		return orders;

	}

	public List<Order> returnAllRequestedOrders(Integer userId) throws DaoException {
		
		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM ORDER WHERE user_id =  ? AND status = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, userId);
			stmt.setInt(2, OrderStatus.REQUESTED.ordinal());
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					do {
						Order order = new Order();
						order.setOrderId(rs.getInt("order_id"));
						order.setUserId(rs.getInt("user_id"));
						order.setServiceId(rs.getInt("service_id"));
						order.setAdressId(rs.getInt("address_id"));
						order.setAmount(rs.getDouble("amount"));
						order.setTimestamp(rs.getDate("timestamp"));
						order.setOrderStatus(rs.getInt("status"));
						orders.add(order);
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
		return orders;

	}

	public List<Order> returnAllCancelledOrders(Integer userId) throws DaoException {
		
		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM ORDER WHERE user_id =  ? AND status = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, userId);
			stmt.setInt(2, OrderStatus.CANCELLED.ordinal());
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					do {
						Order order = new Order();
						order.setOrderId(rs.getInt("order_id"));
						order.setUserId(rs.getInt("user_id"));
						order.setServiceId(rs.getInt("service_id"));
						order.setAdressId(rs.getInt("address_id"));
						order.setAmount(rs.getDouble("amount"));
						order.setTimestamp(rs.getDate("timestamp"));
						order.setOrderStatus(rs.getInt("status"));
						orders.add(order);
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
		return orders;

	}

	public List<Order> returnAllRejectedOrders(Integer userId) throws DaoException {
		
		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM ORDER WHERE user_id =  ? AND status = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, userId);
			stmt.setInt(2, OrderStatus.REJECTED.ordinal());
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					do {
						Order order = new Order();
						order.setOrderId(rs.getInt("order_id"));
						order.setUserId(rs.getInt("user_id"));
						order.setServiceId(rs.getInt("service_id"));
						order.setAdressId(rs.getInt("address_id"));
						order.setAmount(rs.getDouble("amount"));
						order.setTimestamp(rs.getDate("timestamp"));
						order.setOrderStatus(rs.getInt("status"));
						orders.add(order);
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
		return orders;

	}

	public List<Order> returnAllPastCompletedOrders(Integer userId) throws DaoException {
		
		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM ORDER WHERE user_id =  ? AND status = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, userId);
			stmt.setInt(2, OrderStatus.COMPLETED.ordinal());
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					do {
						Order order = new Order();
						order.setOrderId(rs.getInt("order_id"));
						order.setUserId(rs.getInt("user_id"));
						order.setServiceId(rs.getInt("service_id"));
						order.setAdressId(rs.getInt("address_id"));
						order.setAmount(rs.getDouble("amount"));
						order.setTimestamp(rs.getDate("timestamp"));
						order.setOrderStatus(rs.getInt("status"));
						orders.add(order);
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
		return orders;

	}

	public Order returnSpecificOrder(Integer orderId, Integer userId) throws DaoException {
		
		Order order;
		String sql = "SELECT * FROM ORDER WHERE order_id = ? AND user_id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, orderId);
			stmt.setInt(2, userId);
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
						order = new Order();
						order.setOrderId(rs.getInt("order_id"));
						order.setUserId(rs.getInt("user_id"));
						order.setServiceId(rs.getInt("service_id"));
						order.setAdressId(rs.getInt("address_id"));
						order.setAmount(rs.getDouble("amount"));
						order.setTimestamp(rs.getDate("timestamp"));
						order.setOrderStatus(rs.getInt("status"));
						return order;
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
		return null;

	}

	public void cancelOrder(Integer orderId) throws DaoException {

		String sql = "UPDATE ORDER SET status = ? WHERE order_id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, OrderStatus.CANCELLED.ordinal());
			stmt.setInt(2, orderId);
			stmt.executeUpdate();
				
		}
		catch (Exception e) {
			throw new DaoException(e);
		}

	}

	public void rejectOrder(Integer orderId) throws DaoException {

		String sql = "UPDATE ORDER SET status = ? WHERE order_id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
			{
				stmt.setInt(1, OrderStatus.REJECTED.ordinal());
				stmt.setInt(2, orderId);
				stmt.executeUpdate();
					
			}
			catch (Exception e) {
				throw new DaoException(e);
			}
	}
	
	public void acceptOrder(Integer orderId) throws DaoException {

		String sql = "UPDATE ORDER SET status = ? WHERE order_id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, OrderStatus.CONFIRMED.ordinal());
			stmt.setInt(2, orderId);
			stmt.executeUpdate();
				
		}
		catch (Exception e) {
			throw new DaoException(e);
		}

	}
	
	public void completeOrder(Integer orderId) throws DaoException {

		String sql = "UPDATE ORDER SET status = ? WHERE order_id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, OrderStatus.COMPLETED.ordinal());
			stmt.setInt(2, orderId);
			stmt.executeUpdate();
				
		}
		catch (Exception e) {
			throw new DaoException(e);
		}

	}

	public Boolean updateCompletedOrders(Integer serviceId) throws DaoException {

		String sql = "UPDATE SERVICE SET completed_orders = completed_orders + ? WHERE service_id = ?";
		//what to do about user id generation?
		try (Connection conn = DbUtil.createConnection(); 
			PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, 1);
			stmt.setInt(2, serviceId);

			stmt.executeUpdate();
			System.out.println("Count of completed orders increased");
			return true;

		} catch (Exception e) {
			throw new DaoException(e);
		}
}


}
