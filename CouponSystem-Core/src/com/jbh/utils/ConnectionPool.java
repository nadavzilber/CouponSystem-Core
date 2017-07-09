package com.jbh.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionPool {

	private ArrayList<Connection> connectionDB = new ArrayList<Connection>();

	String driver = "org.apache.derby.jdbc.ClientDriver";
	String connection = "jdbc:derby://localhost:1527/CouponSystem";

	private ConnectionPool() {

		try {
			Class.forName(driver);

		} catch (ClassNotFoundException e) {
			System.out.println("Driver not found - Couldn't connect to the pool");
			e.printStackTrace();
		}

		for (int i = 0; i < 5; i++) {
			try {
				connectionDB.add(DriverManager.getConnection(connection, "root", "root"));
			} catch (SQLException e) {
				System.out.println("Connection problem - Couldn't connect to the pool");
				e.printStackTrace();
			}
		}
	}

	private static ConnectionPool pool = new ConnectionPool();

	public static ConnectionPool getInstance() {
		return pool;
	}

	public Connection getConnection() {
		try {
			if (connectionDB.isEmpty()) {
				connectionDB.wait();
			} else
				connectionDB.remove(connectionDB);
		} catch (Exception e) {

			System.out.println("connection unavailable");
		}
		return pool.connectionDB.get(0);
	}

	public void returnConnection(Connection connection) {
		connectionDB.add(connection);
	}

	public void closeAllConnections() {
		try {
			((Connection) connectionDB).close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
