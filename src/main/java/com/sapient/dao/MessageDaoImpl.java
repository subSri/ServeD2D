package com.sapient.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sapient.entity.Message;
import com.sapient.utils.DbUtil;

public class MessageDaoImpl implements MessageDao {

	public Boolean liveMessage(Message message, Integer senderId) throws DaoException {
		if (senderId == message.getSenderId()) {
//			String sql = "INSERT INTO MESSAGE (message_id, sender_id, reciever_id, content, timestamp) VALUES (?,?,?,?,?)";
			String sql = "INSERT INTO MESSAGE (sender_id, reciever_id, content, timestamp) VALUES (?,?,?,?)";
			try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);) {
//				stmt.setInt(1, message.getMessageId());
				stmt.setInt(1, message.getSenderId());
				stmt.setInt(2, message.getReceiverId());
				stmt.setString(3, message.getContent());
				stmt.setDate(4, (java.sql.Date) message.getTimestamp());
				stmt.executeUpdate();
				
				ResultSet key = stmt.getGeneratedKeys();
				if(key.next())
				{
					int id = key.getInt(1);  // or "message_id"
					System.out.println("message sent" + id);
					message.setMessageId(id);
				}
				
				return true;

			} catch (Exception e) {
				throw new DaoException(e);
			}
		} else {
			throw new DaoException("Forbidden Request");
		}
	}

	public List<Message> returnAllMessagesForAnOrder(Integer orderId) throws DaoException {

		List<Message> chats = new ArrayList<Message>();

		String sql = "SELECT message_id,sender_id,reciever_id,content,MESSAGE.timestamp FROM MESSAGE JOIN `ORDER` WHERE (MESSAGE.sender_id = `ORDER`.user_id OR MESSAGE.reciever_id = `ORDER`.user_id) AND `ORDER`.order_id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, orderId);
			try (ResultSet rs = stmt.executeQuery();) {

				if(rs.next()) {
					do {
						Message message = getMessageObj(rs);
						chats.add(message);
					} while (rs.next());

				} else {
					System.out.println("No data found!");
				}

			} catch (Exception e) {
				throw new DaoException(e);
			}

		} catch (Exception e) {
			throw new DaoException(e);
		}
		return chats;

	}

	private Message getMessageObj(ResultSet rs) throws SQLException {
		Message message = new Message();
		message.setMessageId(rs.getInt("message_id"));
		message.setSenderId(rs.getInt("sender_id"));
		message.setReceiverId(rs.getInt("reciever_id"));
		message.setContent(rs.getString("content"));
		message.setTimestamp(rs.getDate("timestamp"));
		return message;
	}

	public Map<Integer, Map<String, String>> returnChatsOfUser(Integer userId) throws DaoException {

		Map<Integer, Map<String, String>> recievers = new HashMap<Integer, Map<String, String>>();
		// Costly Operation
		String sql = "select name,user_id, content,max(timestamp) timestamp from (select reciever_id u_id,content,timestamp from MESSAGE where sender_id=? union select sender_id u_id,content,timestamp from MESSAGE where reciever_id=?) t,USER where user_id=u_id group by user_id";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, userId);
			stmt.setInt(2, userId);
			try (ResultSet rs = stmt.executeQuery();) {

				if (rs.next()) {
					do {
						Map<String, String> entry = new HashMap<>();
						entry.put("user_id", rs.getString("user_id"));
						entry.put("name", rs.getString("name"));
						entry.put("timestamp", rs.getString("timestamp"));
						entry.put("content", rs.getString("content"));

						recievers.put(rs.getInt("user_id"), entry);
					} while (rs.next());

				} else {
					System.out.println("User has made no conversation!");
				}

			} catch (Exception e) {
				throw new DaoException(e);
			}

		} catch (Exception e) {
			throw new DaoException(e);
		}
		return recievers;

	}

	public List<Message> returnAllMessagesForAReceiver(Integer recieverId) throws DaoException {

		List<Message> chats = new ArrayList<Message>();

		String sql = "SELECT message_id,sender_id,reciever_id,content,MESSAGE.timestamp FROM MESSAGE WHERE reciever_id = ?";
		try (Connection conn = DbUtil.createConnection(); PreparedStatement stmt = conn.prepareStatement(sql);) {
			stmt.setInt(1, recieverId);
			try (ResultSet rs = stmt.executeQuery();) {

				if (rs.next()) {
					do {
						Message message = getMessageObj(rs);
						chats.add(message);
					} while (rs.next());

				} else {
					System.out.println("No data found!");
				}

			} catch (Exception e) {
				throw new DaoException(e);
			}

		} catch (Exception e) {
			throw new DaoException(e);
		}
		return chats;

	}

}
