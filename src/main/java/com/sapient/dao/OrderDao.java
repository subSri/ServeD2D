package com.sapient.dao;

import com.sapient.enums.Enums.OrderStatus;
import com.sapient.utils.DbUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.sapient.entity.*;


public class OrderDao {
    
    public Boolean addNewOrder(Order order)
			throws DaoException {
		String sql = "INSERT INTO order (ORDERID, USERID, SERVICEID, ADDRESSID, TIMESTAMP, ORDERSTATUS, AMOUNT, PAYMENTSTATUS) VALUES (?,?,?,?,?,?,?,?)";
		//what to do about user id generation?
		try (Connection conn = DbUtil.createConnection(); 
			PreparedStatement stmt = conn.prepareStatement(sql);
			) {
			stmt.setInt(1, order.getOrderId());
            stmt.setInt(2, order.getUserId());
            stmt.setInt(3, order.getServiceId());
            stmt.setInt(4, order.getAdressId());
			stmt.setDate(5, (Date) order.getTimestamp());
			stmt.setInt(6, order.getOrderStatus());
			stmt.setDouble(7, order.getAmount());
			stmt.setInt(8, order.getPayment_status());

			stmt.executeUpdate();
			System.out.println("new order added");
			return true;

		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public List<Order> returnAllOrders(Integer userId) throws DaoException {
		
		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM orders WHERE USERID =  ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, userId);
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					do {
						Order order = new Order();
						order.setOrderId(rs.getInt("ORDERID"));
						order.setUserId(rs.getInt("USERID"));
						order.setServiceId(rs.getInt("SERVICEID"));
						order.setAdressId(rs.getInt("ADDRESSID"));
						order.setAmount(rs.getDouble("AMOUNT"));
						order.setTimestamp(rs.getDate("TIMESTAMP"));
						order.setOrderStatus(rs.getInt("ORDERSTATUS"));
						order.setPayment_status(rs.getInt("PAYMENTSTATUS"));
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
		String sql = "SELECT * FROM orders WHERE USERID =  ? AND STATUS = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, userId);
			stmt.setInt(2, OrderStatus.CONFIRMED.ordinal());
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					do {
						Order order = new Order();
						order.setOrderId(rs.getInt("ORDERID"));
						order.setUserId(rs.getInt("USERID"));
						order.setServiceId(rs.getInt("SERVICEID"));
						order.setAdressId(rs.getInt("ADDRESSID"));
						order.setAmount(rs.getDouble("AMOUNT"));
						order.setTimestamp(rs.getDate("TIMESTAMP"));
						order.setOrderStatus(rs.getInt("ORDERSTATUS"));
						order.setPayment_status(rs.getInt("PAYMENTSTATUS"));
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

	public List<Order> returnAllPastOrders(Integer userId) throws DaoException {
		
		List<Order> orders = new ArrayList<Order>();
		String sql = "SELECT * FROM orders WHERE USERID =  ? AND STATUS = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, userId);
			stmt.setInt(2, OrderStatus.COMPLETED.ordinal());
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					do {
						Order order = new Order();
						order.setOrderId(rs.getInt("ORDERID"));
						order.setUserId(rs.getInt("USERID"));
						order.setServiceId(rs.getInt("SERVICEID"));
						order.setAdressId(rs.getInt("ADDRESSID"));
						order.setAmount(rs.getDouble("AMOUNT"));
						order.setTimestamp(rs.getDate("TIMESTAMP"));
						order.setOrderStatus(rs.getInt("ORDERSTATUS"));
						order.setPayment_status(rs.getInt("PAYMENTSTATUS"));
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

	public Order returnSpecificOrder(Integer orderId) throws DaoException {
		
		Order order = new Order();
		String sql = "SELECT * FROM orders WHERE ORDERID = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, orderId);
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
						order.setOrderId(rs.getInt("ORDERID"));
						order.setUserId(rs.getInt("USERID"));
						order.setServiceId(rs.getInt("SERVICEID"));
						order.setAdressId(rs.getInt("ADDRESSID"));
						order.setAmount(rs.getDouble("AMOUNT"));
						order.setTimestamp(rs.getDate("TIMESTAMP"));
						order.setOrderStatus(rs.getInt("ORDERSTATUS"));
						order.setPayment_status(rs.getInt("PAYMENTSTATUS"));
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
		return order;

	}

	public void cancelOrder(Integer orderId) throws DaoException {

		String sql = "UPDATE orders SET ORDERSTATUS = ? WHERE ORDERID = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, OrderStatus.CANCELLED.ordinal());
			stmt.setInt(2, orderId);
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					
				}
				else
				{
					System.out.println("Order doesnt exist"); 
				}
			
			}
			catch (Exception e) {
				throw new DaoException(e);
			}
				
		}
		catch (Exception e) {
			throw new DaoException(e);
		}

	}

	public void rejectOrder(Integer orderId) throws DaoException {

		String sql = "UPDATE orders SET ORDERSTATUS = ? WHERE ORDERID = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, OrderStatus.REJECTED.ordinal());
			stmt.setInt(2, orderId);
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
				}
				else
				{
					System.out.println("Order doesnt exist"); 
				}
			
			}
			catch (Exception e) {
				throw new DaoException(e);
			}
				
		}
		catch (Exception e) {
			throw new DaoException(e);
		}

	}
	
	public void acceptOrder(Integer orderId) throws DaoException {

		String sql = "UPDATE orders SET ORDERSTATUS = ? WHERE ORDERID = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, OrderStatus.CONFIRMED.ordinal());
			stmt.setInt(2, orderId);
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
				}
				else
				{
					System.out.println("Order doesnt exist"); 
				}
			
			}
			catch (Exception e) {
				throw new DaoException(e);
			}
				
		}
		catch (Exception e) {
			throw new DaoException(e);
		}

	}
	
	public void completeOrder(Integer orderId) throws DaoException {

		String sql = "UPDATE orders SET ORDERSTATUS = ? WHERE ORDERID = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) 
		{
			stmt.setInt(1, OrderStatus.COMPLETED.ordinal());
			stmt.setInt(2, orderId);
			try(ResultSet rs = stmt.executeQuery();)
			{
				if(rs.next()) {
					updateCompletedOrders(rs.getInt("service_id"));
				}
				else
				{
					System.out.println("Order doesnt exist"); 
				}
			
			}
			catch (Exception e) {
				throw new DaoException(e);
			}
				
		}
		catch (Exception e) {
			throw new DaoException(e);
		}

	}

	public Boolean updateCompletedOrders(Integer serviceId) throws DaoException {

		String sql = "UPDATE service SET completed_orders = completed_orders + ? WHERE service_id = ?";
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
