package com.jbh.facade;

import java.util.Collection;

import com.jbh.dao.CompanyDAO;
import com.jbh.dao.CouponDAO;
import com.jbh.dao.CustomerDAO;
import com.jbh.entities.Company;
import com.jbh.entities.Coupon;
import com.jbh.entities.Customer;

public class AdminFacade implements CouponClientFacade {

	private CompanyDAO companyDAO = new CompanyDAO();
	private CouponDAO couponDAO = new CouponDAO();
	private CustomerDAO customerDAO = new CustomerDAO();

	public AdminFacade() {
	}

	@Override
	public String toString() {
		return "admin";
	}

	public void createCompany(Company company) throws Exception {

		if (companyDAO.getAllCompanies() != null)
			if (companyDAO.getAllCompanies().contains(company)) {
				throw new Exception("AdminFacade: " + company.getName()
						+ " is already taken! There can't be 2 companies with the same name!");
			}
		companyDAO.createCompany(company);
	}

	public void removeCompany(Company company) throws Exception {
		try {
			Collection<Coupon> coupons = couponDAO.getCompanyCoupons(company.getId());
			for (Coupon coupon : coupons) {
				couponDAO.removeCompanyCoupon(coupon.getId());
				couponDAO.removeCustomerCoupon(coupon.getId());
				couponDAO.removeCoupon(coupon);
			}
			companyDAO.removeCompany(company);
		} catch (Exception e) {
			throw new Exception("AdminFacade: Error encountered while attempting to delete company.");
		}
	}

	public void updateCompany(Company company) throws Exception {
		try {
			companyDAO.updateCompany(company);
		} catch (Exception e) {
			throw new Exception("AdminFacade: Error encountered while attempting to update company.");
		}
	}

	public Company getCompany(long id) throws Exception {
		try {
			return companyDAO.getCompany(id);
		} catch (Exception e) {
			throw new Exception("AdminFacade: Error encountered while attempting to retrieve company.");
		}
	}

	public Collection<Company> getAllCompanies() throws Exception {
		try {
			return companyDAO.getAllCompanies();
		} catch (Exception e) {
			throw new Exception("AdminFacade: Error encountered while attempting to retrieve companies.");
		}
	}

	public void createCustomer(Customer customer) throws Exception {
		if (customerDAO.getAllCustomers() != null)
			if (customerDAO.getAllCustomers().contains(customer)) {
				throw new Exception(
						customer.getName() + " is already taken! There can't be 2 customers with the same name!");
			}
		customerDAO.createCustomer(customer);
	}

	public void removeCustomer(Customer customer) throws Exception {
		try {
			Collection<Coupon> coupons = customerDAO.getCustomerCoupons(customer.getId());
			for (Coupon coupon : coupons) {
				couponDAO.removeCustomerCoupon(coupon.getId());
			}
			customerDAO.removeCustomer(customer);
		} catch (Exception e) {
			throw new Exception("AdminFacade: Error encountered while attempting to delete customer.");
		}
	}

	public void updateCustomer(Customer customer) throws Exception {
		try {
			customerDAO.updateCustomer(customer);
		} catch (Exception e) {
			throw new Exception("AdminFacade: Error encountered while attempting to update customer.");
		}
	}

	public Customer getCustomer(long id) throws Exception {
		try {
			return customerDAO.getCustomer(id);
		} catch (Exception e) {
			throw new Exception("AdminFacade: Error encountered while attempting to retrieve customer.");
		}
	}

	public Collection<Customer> getAllCustomers() throws Exception {
		try {
			return customerDAO.getAllCustomers();
		} catch (Exception e) {
			throw new Exception("AdminFacade: Error encountered while attempting to retrieve customers.");
		}
	}

	@Override
	public CouponClientFacade login(String name, String password) throws Exception {
		if (name.equalsIgnoreCase("admin") && password.equals("1234"))
			return new AdminFacade();
		else
			throw new Exception("AdminFacade: Admin Login FAILED");
	}
}
