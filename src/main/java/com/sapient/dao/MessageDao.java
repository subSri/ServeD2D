package com.sapient.dao;

import java.util.List;
import java.util.Map;

import com.sapient.entity.Message;

public interface MessageDao {

	public Boolean liveMessage(Message message, Integer senderId) throws DaoException;

	public List<Message> returnAllMessagesForAnOrder(Integer orderId) throws DaoException;

	public Map<Integer, Map<String, String>> returnChatsOfUser(Integer userId) throws DaoException;

}
