package com.sapient.dao;

import java.util.List;
import java.util.Map;

import com.sapient.entity.*;

public interface OrderDao {
	public Boolean addNewOrder(Order order) throws DaoException;

	public List<Map<String, String>> returnAllOrders(Integer userId) throws DaoException;

	public List<Order> returnAllOrdersForProvider(Integer provId) throws DaoException;

	public List<Order> returnAllIncompleteOrders(Integer userId) throws DaoException;

	public List<Order> returnAllRequestedOrders(Integer userId) throws DaoException;

	public List<Order> returnAllCancelledOrders(Integer userId) throws DaoException;

	public List<Order> returnAllRejectedOrders(Integer userId) throws DaoException;

	public List<Order> returnAllPastCompletedOrders(Integer userId) throws DaoException;

	public Order returnSpecificOrder(Integer orderId, Integer userId) throws DaoException;

	public void cancelOrder(Integer orderId) throws DaoException;

	public void rejectOrder(Integer orderId) throws DaoException;

	public void acceptOrder(Integer orderId) throws DaoException;

	public void completeOrder(Integer orderId) throws DaoException;

	public Boolean updateCompletedOrders(Integer serviceId) throws DaoException;

}
