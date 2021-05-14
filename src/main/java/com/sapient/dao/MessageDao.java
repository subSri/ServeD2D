package com.sapient.dao;

import java.util.List;

import com.sapient.entity.Message;

public interface MessageDao {
	
	public Boolean liveMessage(Message message) throws DaoException;
	public List<Message> returnAllMessagesForAnOrder(Integer orderId) throws DaoException;
	public List<Integer> returnChatsOfUser(Integer userId) throws DaoException;
	 
}
