package com.sapient.entity;

import java.sql.Timestamp;
import lombok.Data;

@Data
public class Message {
	private int id;
	private int sender_id;
	private int receiver_id;
	private String content;
	private Timestamp timestamp;
	
	

}
