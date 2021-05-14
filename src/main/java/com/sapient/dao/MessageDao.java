package com.sapient.dao;

import java.util.List;

import com.sapient.entity.Message;

public interface MessageDao {
	
	public Boolean liveMessage(Message message) throws DaoException;
	public List<Message> returnAllMessagesForAnOrder(Integer order_id) throws DaoException;
	public List<Integer> returnChatsOfUser(Integer user_id) throws DaoException;
	 
}
