package com.revature.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

	public static Connection getConnection() throws SQLException{

		Connection conn = DriverManager.getConnection(
				System.getenv("DB_url"), 
				System.getenv("DB_username"), 
				System.getenv("DB_password")
				);
		
		//If you want all of your connection instances to NOT use autocommit:
//		conn.setAutoCommit(false);
		
		return conn;

	}
}