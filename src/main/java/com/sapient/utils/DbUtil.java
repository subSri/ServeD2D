package com.sapient.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public final class DbUtil {

	static Connection connection;
	private DbUtil() {
	}

	public static Connection createConnection() throws SQLException, ClassNotFoundException {

		if (connection != null){
			return connection;
		}

		ResourceBundle rb = ResourceBundle.getBundle("jdbc-data");
		String driverClassName = rb.getString("jdbc.driverClassName");
		String url = rb.getString("jdbc.url");
		String username = rb.getString("jdbc.username");
		String password = rb.getString("jdbc.password");

		Class.forName(driverClassName);
		connection = DriverManager.getConnection(url, username, password);
		return connection;
	}


	// public static void main(String[] args) throws SQLException, ClassNotFoundException{
	// 	System.out.println("Connection is : "+ DbUtil.createConnection());
		
	// }

}