package com.jbh.facade;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.jbh.dao.CompanyDAO;
import com.jbh.dao.CouponDAO;
import com.jbh.entities.Coupon;
import com.jbh.enums.CouponType;

public class CompanyFacade implements CouponClientFacade {

	// DAO
	private CompanyDAO companyDAO = new CompanyDAO();
	private CouponDAO couponDAO = new CouponDAO();

	private long loggedCompany = 0;

	public long getLoggedCompany() {
		return loggedCompany;
	}

	public void setLoggedCompany(long loggedCompany) {
		this.loggedCompany = loggedCompany;
	}

	public CompanyFacade() {
	}

	public void createCoupon(Coupon coupon) throws Exception {

		if (couponDAO.getAllCoupons().contains(coupon)) {
			throw new Exception("CompanyFacade: " + coupon.getTitle()
					+ " is already taken! There can't be 2 coupons with the same name!");
		} else {
			couponDAO.createCoupon(coupon);
			companyDAO.linkCompanyCoupon(loggedCompany, coupon.getId());
		}
	}

	public void removeCoupon(Coupon coupon) throws Exception {
		couponDAO.removeCompanyCoupon(coupon.getId());
		couponDAO.removeCustomerCoupon(coupon.getId());
		couponDAO.removeCoupon(coupon);
	}

	public void updateCoupon(Coupon coupon) throws Exception {
		couponDAO.updateCoupon(coupon);
	}

	public Coupon getCoupon(long id) throws Exception {
		return couponDAO.getCoupon(id);
	}

	public Collection<Coupon> getAllCoupons() throws Exception {
		Collection<Coupon> coupons = new ArrayList<>();
		coupons = couponDAO.getCompanyCoupons(loggedCompany);
		return coupons;
	}

	public Collection<Coupon> getCouponByType(CouponType couponType) throws Exception {
		Collection<Coupon> couponsOfType = new ArrayList<>();
		couponsOfType = couponDAO.getCompanyCoupons(loggedCompany);
		Iterator<Coupon> iter = couponsOfType.iterator();
		while (iter.hasNext()) {
			Coupon coup = iter.next();
			if (!(coup.getType().equals(couponType)))
				iter.remove();
		}
		System.out.println("Purchased coupons of type " + couponType + " retrieved!");
		return couponsOfType;
	}

	@Override
	public CouponClientFacade login(String name, String password) throws Exception {
		if (companyDAO.login(name, password)) {
			CompanyFacade clientFacade = new CompanyFacade();
			clientFacade.setLoggedCompany(companyDAO.getLoginID());
			return clientFacade;
		} else
			throw new Exception("CompanyFacade: Company Login FAILED");
	}

	@Override
	public String toString() {
		return "company";
	}

}
