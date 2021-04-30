package com.sapient.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public final class DbUtil {

	private DbUtil() {
	}

	public static Connection createConnection() throws SQLException, ClassNotFoundException {
		ResourceBundle rb = ResourceBundle.getBundle("jdbc-data");
		String driverClassName = rb.getString("jdbc.driverClassName");
		String url = rb.getString("jdbc.url");
		String username = rb.getString("jdbc.username");
		String password = rb.getString("jdbc.password");

		Class.forName(driverClassName);
		return DriverManager.getConnection(url, username, password);
	}

}