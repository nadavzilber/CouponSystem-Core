/**
 *  Created by Nadav Zilberfarb
 *  nadavzilber@gmail.com
 *   **/

package com.jbh.main;
import java.util.Collection;
import com.jbh.entities.Coupon;
import com.jbh.enums.ClientType;
import com.jbh.enums.CouponType;
import com.jbh.facade.AdminFacade;
import com.jbh.facade.CompanyFacade;
import com.jbh.facade.CustomerFacade;

public class TestMain {

	public static void main(String[] args) throws Exception {

		System.out.println("********************************");
		System.out.println(".....  WELCOME TO THE DEMO .....");
		System.out.println("********************************");

		
		// Un-comment if you want to create the DB tables 
		//BuildDB.createDb();

		// Un-comment if you want to reset all DB table values
		//BuildDB.emptyDB();

		go();

		System.out.println("********************************");
		System.out.println("..........Finished run..........");
		System.out.println("......... END OF DEMO ..........");
	}

	public static void go() throws Exception {
		try {
			System.out.println("Staring Demo............");

			CouponManager main = CouponManager.getInstance();
			AdminFacade admin = (AdminFacade) CouponManager.getInstance().login("admin", "1234", ClientType.ADMIN);

			admin.createCompany(main.sampleObjects.compA);
			admin.createCompany(main.sampleObjects.compB);
			admin.createCompany(main.sampleObjects.compC);

			admin.createCustomer(main.sampleObjects.Nadav);
			admin.createCustomer(main.sampleObjects.Nina);

			System.out.println("Logging in as compA");
			CompanyFacade comp = (CompanyFacade) CouponManager.getInstance().login("compA", "compA",
					ClientType.COMPANY);

			comp.createCoupon(main.sampleObjects.coupon1);
			comp.createCoupon(main.sampleObjects.coupon2);
			comp.createCoupon(main.sampleObjects.coupon3);
			comp.createCoupon(main.sampleObjects.coupon4);
			comp.createCoupon(main.sampleObjects.coupon5);

			System.out.println("********************************");
			System.out.println("Retrieving all coupons owned by company");
			System.out.println(comp.getAllCoupons());
			System.out.println("********************************");
			System.out.println("Retrieving all companies");
			System.out.println(admin.getAllCompanies());

			System.out.println("********************************");
			System.out.println("Logging in as compC");

			comp = (CompanyFacade) CouponManager.getInstance().login("compC", "compC", ClientType.COMPANY);

			System.out.println("********************************");
			System.out.println("Customer nadav is logging in......");

			CustomerFacade custF = (CustomerFacade) CouponManager.getInstance().login("nadav", "nadav",
					ClientType.CUSTOMER);

			// CustomerDAO cus = new CustomerDAO();

			System.out.println("Logged customer ID:" + custF.getLoggedCustomer());
			System.out.println(admin.getCustomer(custF.getLoggedCustomer()));
			System.out.println("********************************");
			System.out.println("Purchase #1");
			custF.purchaseCoupon(main.sampleObjects.coupon1);
			System.out.println("********************************");
			System.out.println("Purchase #2");
			custF.purchaseCoupon(main.sampleObjects.coupon2);
			System.out.println("********************************");
			System.out.println("Purchase #3");
			custF.purchaseCoupon(main.sampleObjects.coupon3);
			System.out.println("********************************");
			System.out.println("Purchase #4");
			custF.purchaseCoupon(main.sampleObjects.coupon4);
			System.out.println("********************************");

			System.out.println("Displaying all of Nadav's coupons");
			System.out.println(custF.getAllPurchasedCoupons());
			Collection<Coupon> allPurchased = custF.getAllPurchasedCoupons();
			for (Coupon coupon : allPurchased) {
				System.out.println("Purchased: " + coupon);
			}
			System.out.println("********************************");
			System.out.println("Displaying all of Nadav's food coupons");
			System.out.println(custF.getAllPurchasedCouponsByType(CouponType.FOOD));
			System.out.println("********************************");
			System.out.println("Removing coupon 4");
			comp.removeCoupon(main.sampleObjects.coupon4);
			System.out.println("********************************");
			System.out.println("Displaying new coupons list after deletion");
			System.out.println(comp.getAllCoupons());
			System.out.println("********************************");
			System.out.println("Removing compA");
			admin.removeCompany(main.sampleObjects.compA);
			System.out.println("********************************");
			System.out.println("Displaying Nadav's coupons, not including coupons by CompA");
			System.out.println(custF.getAllPurchasedCoupons());
			System.out.println("********************************");
			System.out.println("Displaying all customers");
			System.out.println(admin.getAllCustomers());
			System.out.println("********************************");
			System.out.println("************Displaying all companies************");
			System.out.println(admin.getAllCompanies());
			System.out.println("*************************");
			System.out.println("Showing logged company ID:" + comp.getLoggedCompany());
			System.out.println(admin.getCompany(comp.getLoggedCompany()));

		} catch (Exception e) {
			System.out.println("! - ERROR - !");
			e.printStackTrace();
			System.out.println("TestMain: Demo Failed");
		}

	}
}
