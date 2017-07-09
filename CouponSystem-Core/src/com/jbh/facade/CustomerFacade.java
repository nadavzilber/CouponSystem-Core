package com.jbh.facade;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.ListIterator;

import com.jbh.dao.CompanyDAO;
import com.jbh.dao.CouponDAO;
import com.jbh.dao.CustomerDAO;
import com.jbh.entities.Coupon;
import com.jbh.enums.CouponType;

public class CustomerFacade implements CouponClientFacade {

	// DAO
	CustomerDAO customerDAO = new CustomerDAO();
	CouponDAO couponDAO = new CouponDAO();
	CompanyDAO companyDAO = new CompanyDAO();

	private long loggedCustomer = 0;

	public long getLoggedCustomer() {
		return loggedCustomer;
	}

	public void setLoggedCustomer(long customerID) {
		this.loggedCustomer = customerID;
	}

	public CustomerFacade() {
	}

	// Customer customer = new Customer();
	// Coupon coupon = new Coupon();

	/*
	 * need to check if coupon exists then how much of it exists then if its
	 * valid then if the buyer already has any
	 * 
	 */
	public void purchaseCoupon(Coupon coupon) throws Exception {
		Coupon targetCoupon;
		targetCoupon = couponDAO.getCoupon(coupon.getId());
		// Collection<Coupon> allCoupons = couponDAO.getAllCoupons();
		// if (allCoupons.contains(targetCoupon)) {
		Collection<Coupon> customerCoupons = new ArrayList<>();
		customerCoupons = customerDAO.getCustomerCoupons(getLoggedCustomer());
		if (!customerCoupons.contains(targetCoupon))
			if (targetCoupon.getEndDate().after(new Date(System.currentTimeMillis())))
				if (targetCoupon.getAmount() > 0) {
					targetCoupon.setAmount(targetCoupon.getAmount() - 1);
					// if (targetCoupon.getAmount()==0){
					// couponDAO.removeCoupon(targetCoupon);
					// couponDAO.removeCompanyCoupon(targetCoupon.getId());
					// couponDAO.removeCustomerCoupon(targetCoupon.getId());
					// System.out.println(targetCoupon + "out of stock");
					// } else {
					couponDAO.updateCoupon(targetCoupon);
					customerDAO.linkCustomerCoupon(getLoggedCustomer(), targetCoupon.getId());
					System.out
							.println("Coupon " + targetCoupon.getTitle() + " " + targetCoupon.getId() + " Purchased!");
					System.out.println("Expires at: " + targetCoupon.getEndDate().toString());
				}
	}

	public Collection<Coupon> getAllPurchasedCoupons() throws SQLException {
		Collection<Coupon> purchased = customerDAO.getCustomerCoupons(loggedCustomer);
		System.out.println("Purchased coupons retrieved!");
		return purchased;
	}

	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) throws SQLException {
		Collection<Coupon> purchasedOfType = new ArrayList<>();
		purchasedOfType = customerDAO.getCustomerCoupons(loggedCustomer);
		Iterator<Coupon> iter = purchasedOfType.iterator();
		Coupon coup = new Coupon();
		while (iter.hasNext()) {
			coup = iter.next();
			if (!type.equals(coup.getType())) {
				iter.remove();
			}
		}
		System.out.println("Purchased coupons of type " + type + " retrieved!");

		return purchasedOfType;
	}

	public Collection<Coupon> getAllPurchasedCouponsByPrice(double price) throws SQLException {
		Collection<Coupon> purchasedOfPrice = new ArrayList<>();
		purchasedOfPrice = customerDAO.getCustomerCoupons(loggedCustomer);
		Iterator<Coupon> iter = purchasedOfPrice.iterator();
		while (iter.hasNext()) {
			Coupon coup = iter.next();
			if (coup.getPrice() < price)
				iter.remove();
		}
		System.out.println("Purchased coupons that cost at least " + price + " retrieved!");
		return purchasedOfPrice;
	}

	@Override
	public CouponClientFacade login(String name, String password) throws Exception {
		if (customerDAO.login(name, password)) {
			CustomerFacade clientFacade = new CustomerFacade();
			clientFacade.setLoggedCustomer(customerDAO.getLoginID());
			return clientFacade;
		} else
			throw new Exception("CustomerFacade: Customer Login FAILED");
	}

	@Override
	public String toString() {
		return "customer";
	}

}
