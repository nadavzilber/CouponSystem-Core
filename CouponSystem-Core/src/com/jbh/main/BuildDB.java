package com.jbh.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class BuildDB {

	public static String dbConnection = "jdbc:derby://localhost:1527/CouponSystem";

	public static void createDb() throws SQLException {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection(dbConnection)) {
			System.out.println("Connect to " + connection);

			String buildCompanyTbl = "create table COUPON_DB.COMPANY(ID integer not null primary key generated always as identity (START WITH 1, INCREMENT BY 1), NAME varchar(20), PASSWORD varchar(10), EMAIL varchar(30))";

			String buildCouponTbl = "Create table COUPON_DB.COUPON (ID integer NOT NULL primary key generated always as identity (START WITH 1, INCREMENT BY 1),TITLE varchar(30),START_DATE date,END_DATE date, AMOUNT integer,COUPON_TYPE varchar(30),MESSAGE varchar(30),PRICE integer,IMAGE varchar(30))";

			String buildCustomerTbl = "create table COUPON_DB.CUSTOMER (ID integer NOT NULL primary key generated always as identity (START WITH 1, INCREMENT BY 1),NAME varchar(20),PASSWORD varchar(10))";

			String buildCompanyCouponTbl = "create table COUPON_DB.COMPANY_COUPON (COMPANY_ID integer, COUPON_ID integer,  primary key(COMPANY_ID, COUPON_ID))";

			String buildCustomerCouponTbl = "create table COUPON_DB.CUSTOMER_COUPON (CUSTOMER_ID integer, COUPON_ID integer, primary key (CUSTOMER_ID, COUPON_ID))";

			Statement stmt = connection.createStatement();
			stmt.executeUpdate(buildCompanyTbl);
			stmt.executeUpdate(buildCouponTbl);
			stmt.executeUpdate(buildCustomerTbl);
			stmt.executeUpdate(buildCompanyCouponTbl);
			stmt.executeUpdate(buildCustomerCouponTbl);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("BuilderDB: Failed to build DB tables");
		}
		System.out.println("Tables: COMPANY, CUSTOMER, COUPON, COMPANY_COUPON, CUSTOMER_COUPON created");
		System.out.println("---- DB Build Complete ----");

	}

	public static void emptyDB() throws SQLException {
		try {
			Class.forName("org.apache.derby.jdbc.ClientDriver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		try (Connection connection = DriverManager.getConnection(dbConnection)) {
			System.out.println("Connect to " + connection);

			String emptyCompanyTbl = "TRUNCATE TABLE Coupon_Db.company";

			String emptyCouponTbl = "TRUNCATE TABLE Coupon_Db.coupon";

			String emptyCustomerTbl = "TRUNCATE TABLE Coupon_Db.customer";

			String emptyCompanyCouponTbl = "TRUNCATE TABLE Coupon_Db.company_coupon";

			String emptyCustomerCouponTbl = "TRUNCATE TABLE Coupon_Db.customer_coupon";

			Statement stmt = connection.createStatement();
			stmt.executeUpdate(emptyCompanyTbl);
			stmt.executeUpdate(emptyCouponTbl);
			stmt.executeUpdate(emptyCustomerTbl);
			stmt.executeUpdate(emptyCompanyCouponTbl);
			stmt.executeUpdate(emptyCustomerCouponTbl);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("BuilderDB: Failed to empty DB tables");
		}
		System.out.println("Tables: COMPANY, CUSTOMER, COUPON, COMPANY_COUPON, CUSTOMER_COUPON emptied");
		System.out.println("---- DB Clean Complete ----");

	}
}
