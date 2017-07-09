package com.jbh.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;
import com.jbh.entities.Coupon;
import com.jbh.enums.CouponType;
import com.jbh.utils.ConnectionPool;

public class CouponDAO implements ICouponDAO {

	private Connection connection;

	@Override
	public void createCoupon(Coupon coupon) throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String createSQL = "INSERT INTO COUPON_DB.COUPON" + "(TITLE,START_DATE,END_DATE,AMOUNT,"
					+ "COUPON_TYPE,MESSAGE,PRICE,IMAGE) " + "VALUES (?,?,?,?,?,?,?,?)";
			PreparedStatement pStatement = connection.prepareStatement(createSQL, Statement.RETURN_GENERATED_KEYS);
			pStatement.setString(1, coupon.getTitle());
			pStatement.setDate(2, new java.sql.Date(coupon.getStartDate().getTime()));
			pStatement.setDate(3, new java.sql.Date(coupon.getEndDate().getTime()));
			pStatement.setInt(4, coupon.getAmount());
			pStatement.setString(5, coupon.getType().toString());
			pStatement.setString(6, coupon.getMessage());
			pStatement.setDouble(7, coupon.getPrice());
			pStatement.setString(8, coupon.getImage());
			int affectedRows = pStatement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("CouponDAO: Create Coupon FAILED. No Rows Affected");
			}
			ResultSet generatedKeys = pStatement.getGeneratedKeys();
			if (generatedKeys.next()) {
				coupon.setId(generatedKeys.getLong(1));
			} else {
				throw new SQLException("CouponDAO: Create Coupon FAILED, No ID Was Obtained.");
			}
			System.out.println("Coupon " + coupon.getTitle() + " was created!");
		} catch (SQLException e) {
			throw new SQLException("couponDAO - createCoupon FAILED");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public void removeCoupon(Coupon coupon) throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String removeSQL = "DELETE FROM COUPON_DB.COUPON WHERE ID=?";
			PreparedStatement pStatement3 = connection.prepareStatement(removeSQL);
			pStatement3.setLong(1, coupon.getId());
			pStatement3.executeUpdate();

			System.out.println("Removing the coupon: " + coupon.getTitle());

		} catch (SQLException e) {
			throw new SQLException("CouponDAO: Remove Coupon FAILED");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public void updateCoupon(Coupon coupon) throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String updateSQL = "UPDATE COUPON_DB.COUPON SET TITLE=?, START_DATE=?, END_DATE=?, AMOUNT=?, MESSAGE=?, PRICE=? WHERE ID=?";
			PreparedStatement pStatement = connection.prepareStatement(updateSQL);
			pStatement.setString(1, coupon.getTitle());
			pStatement.setDate(2, new java.sql.Date(coupon.getStartDate().getTime()));
			pStatement.setDate(3, new java.sql.Date(coupon.getEndDate().getTime()));
			pStatement.setInt(4, coupon.getAmount());
			pStatement.setString(5, coupon.getMessage());
			pStatement.setDouble(6, coupon.getPrice());
			pStatement.setLong(7, coupon.getId());
			int affectedRows = pStatement.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("CouponDAO: update Coupon FAILED. No Rows Affected");
			}
			System.out.println("Coupon " + coupon.getTitle() + " was updated!");
		} catch (SQLException e) {
			throw new SQLException("CouponDAO: Update Coupon FAILED");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

	}

	@Override
	public Coupon getCoupon(long id) throws SQLException {
		Coupon coupon = new Coupon();
		try {
			connection = ConnectionPool.getInstance().getConnection();
			String getSQL = "SELECT * FROM COUPON_DB.COUPON WHERE ID=?";
			PreparedStatement pStatement = connection.prepareStatement(getSQL);
			pStatement.setLong(1, id);
			ResultSet result = pStatement.executeQuery();
			if (result != null) {
				while (result.next()) {
					coupon.setTitle(result.getString("TITLE"));
					coupon.setStartDate(result.getDate("START_DATE"));
					coupon.setEndDate(result.getDate("END_DATE"));
					coupon.setAmount(result.getInt("AMOUNT"));
					coupon.setType(CouponType.valueOf(result.getString("COUPON_TYPE")));
					coupon.setMessage(result.getString("MESSAGE"));
					coupon.setPrice(result.getInt("PRICE"));
					coupon.setImage(result.getString("IMAGE"));
					coupon.setId(id);
					// System.out.println("Coupon " + coupon.getTitle() + " was
					// retrieved!");
				}
			}
		} catch (SQLException e) {
			throw new SQLException("CouponDAO: Get Coupon FAILED");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return coupon;
	}

	@Override
	public Collection<Coupon> getAllCoupons() throws SQLException {

		Collection<Coupon> coupons = new ArrayList<Coupon>();

		try {
			connection = ConnectionPool.getInstance().getConnection();

			String getAllSQL = "SELECT ID FROM COUPON_DB.COUPON";
			PreparedStatement pStatement = connection.prepareStatement(getAllSQL);
			ResultSet result = pStatement.executeQuery();

			if (result != null) {
				while (result.next()) {
					Coupon coupon = getCoupon(result.getLong("ID"));
					coupons.add(coupon);
				}

				System.out.println("All coupons retrieved!");

			}
		} catch (SQLException e) {
			throw new SQLException("CouponDAO: Get All Coupons FAILED");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return coupons;
	}

	@Override
	public Collection<Coupon> getCompanyCoupons(long companyId) throws SQLException {
		Collection<Coupon> coupons = new ArrayList<Coupon>();
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String getCompanyCouponsSQL = "SELECT COUPON_ID FROM COUPON_DB.COMPANY_COUPON WHERE COMPANY_ID = ?";
			PreparedStatement pStatement = connection.prepareStatement(getCompanyCouponsSQL);
			pStatement.setLong(1, companyId);
			ResultSet result = pStatement.executeQuery();
			System.out.println(result);
			if (result != null) {
			while (result.next()) {
				long id = result.getLong("COUPON_ID");
				Coupon coupon = getCoupon(result.getLong(1));
				coupons.add(coupon);
			}
			System.out.println("Company ID-" + companyId + " : all coupons retrieved!");
			}
		} catch (SQLException e) {
			throw new SQLException("CouponDAO: Get Company Coupons FAILED");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return coupons;
	}

	@Override
	public Collection<Coupon> getCouponByType(CouponType couponType) throws SQLException {
		Collection<Coupon> coupons = new ArrayList<>();
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String getTypeSQL = "SELECT ID FROM COUPON_DB.COUPON WHERE COUPON_TYPE=?";
			PreparedStatement pStatement = connection.prepareStatement(getTypeSQL);
			pStatement.setString(1, couponType.toString());
			ResultSet result = pStatement.executeQuery();

			int ofType = 0;
			if (result != null) {
				while (result.next()) {
					Coupon coupon = getCoupon(result.getLong("ID"));
					// Coupon coupon = new Coupon();
					// coupon.setId(result.getLong("ID"));
					if (coupon.getId() != 0) {
						coupons.add(this.getCoupon(coupon.getId()));
						ofType++;
					}
				}
				System.out.println("--- End of " + couponType.toString() + " coupon list");

			}
			if (ofType == 0) {
				System.out.println("Coupon of the type " + couponType.toString() + " was not found");

			}
		} catch (SQLException e) {
			throw new SQLException("CouponDAO: Get Coupons By Type FAILED");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
		return coupons;
	}

	@Override
	public void removeCompanyCoupon(long couponId) throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String linkSQL = "DELETE FROM COUPON_DB.COMPANY_COUPON WHERE COUPON_ID=?";
			PreparedStatement pStatement = connection.prepareStatement(linkSQL);
			pStatement.setLong(1, couponId);
			pStatement.executeUpdate();
			System.out.println("Coupon " + couponId + " was deleted and unlinked from all companies");

		} catch (SQLException e) {
			throw new SQLException("CouponDAO: Remove Company Coupon FAILED");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public void removeCustomerCoupon(long couponId) throws SQLException {
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String linkSQL = "DELETE FROM COUPON_DB.CUSTOMER_COUPON WHERE 'COUPON_ID'=?";
			PreparedStatement pStatement = connection.prepareStatement(linkSQL);
			pStatement.setLong(1, couponId);
			pStatement.executeUpdate();
			System.out.println("Coupon " + couponId + " was deleted and unlinked from all customers");

		} catch (SQLException e) {
			throw new SQLException("CouponDAO: Remove Customer Coupon FAILED");
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}
	}

	@Override
	public Boolean titleExist(Coupon coupon) throws SQLException {
		int numOfTitles = 0;
		try {
			connection = ConnectionPool.getInstance().getConnection();

			String titleSQL = "SELECT COUNT (TITLE) AS NUMOFTITLES FROM COUPON WHERE TITLE=?";
			connection = ConnectionPool.getInstance().getConnection();
			PreparedStatement pStatement = connection.prepareStatement(titleSQL);
			pStatement.setString(1, coupon.getTitle());
			ResultSet result = pStatement.executeQuery();

			while (result.next()) {
				numOfTitles = result.getInt("NUMOFTITLES");

			}
			if (numOfTitles > 0) {
				return true;
			}

		} catch (SQLException e) {
			throw new SQLException("CouponDAO: Title Exist FAILED ", e);
		} finally {
			ConnectionPool.getInstance().returnConnection(connection);
		}

		return false;
	}

	public Date getTime() {
		java.util.Date today = new java.util.Date();
		today = Calendar.getInstance().getTime();
		return (Date) today;
	}

	public void removeExpiredCoupon() throws SQLException {
		Collection<Coupon> taskList = null;
		CouponDAO couponTask = new CouponDAO();
		try {
			taskList = (ArrayList<Coupon>) couponTask.getAllCoupons();
		} catch (SQLException e) {
			throw new SQLException("CouponDAO: Remove Expired Coupon FAILED.");
		}
		Iterator<Coupon> iter = taskList.iterator();
		if (iter != null) {
			while (iter.hasNext()) {
				Coupon currentCoupon = iter.next();
				if (!currentCoupon.getEndDate().after(getTime())) {
					try {
						couponTask.removeCoupon(currentCoupon);
					} catch (SQLException e) {
						throw new SQLException("CouponDAO: Remove Expired Coupon FAILED.");
					}

				}
			}
		}
	}
}
