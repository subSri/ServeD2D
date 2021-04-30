package com.sapient.entity;
import java.sql.Timestamp;

import lombok.Data;

public class Message {
	private int id;
	private int sender_id;
	private int receiver_id;
	private String content;
	private Timestamp timestamp;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSender_id() {
		return sender_id;
	}
	public void setSender_id(int sender_id) {
		this.sender_id = sender_id;
	}
	public int getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(int receiver_id) {
		this.receiver_id = receiver_id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Timestamp getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	

}
