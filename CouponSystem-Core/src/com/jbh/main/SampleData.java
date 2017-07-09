package com.jbh.main;

import java.util.Calendar;
import java.util.Date;

import com.jbh.entities.Company;
import com.jbh.entities.Coupon;
import com.jbh.entities.Customer;
import com.jbh.enums.CouponType;
import com.jbh.facade.AdminFacade;
import com.jbh.facade.CompanyFacade;
import com.jbh.facade.CustomerFacade;

public class SampleData {

	SampleDates date = new SampleDates();
	CompanyFacade cf = new CompanyFacade();
	CustomerFacade customer = new CustomerFacade();
	AdminFacade admin = new AdminFacade();

	
	// demo customers
	Customer c1 = new Customer("1","1");
	Customer Nadav = new Customer("nadav", "nadav");
	Customer Nina = new Customer("nina", "nina");
	Customer Shmek = new Customer("shmek", "shmek");

	// demo companies
	Company compA = new Company("compA", "compA", "compA@com");  
	Company compB = new Company("compB", "compB", "compB@com"); 
	Company compC = new Company("compC", "compC", "compC@com"); 
	Company compD = new Company("compD", "compD", "compD@com"); 
	Company compE = new Company("compE", "compE", "compE@com"); 

	// demo coupons
Coupon coupon1 = new Coupon("test1", date.startDate1(), date.endDate1(), 3, CouponType.FOOD, "healthy", 30.01, "msg",
10);
Coupon coupon2 = new Coupon("test2", date.startDate2(), date.endDate2(), 15, CouponType.FOOD, "food", 40.50, "msg", 10);
Coupon coupon3 = new Coupon("test3", date.startDate3(), date.endDate3(), 25, CouponType.FURNITURE, "furn", 30, "msg",
10);
Coupon coupon4 = new Coupon("test4", date.startDate4(), date.endDate4(), 54, CouponType.FOOD, "gadgets", 15.15,
"msg", 10);
Coupon coupon5 = new Coupon("test5", date.startDate1(), date.endDate1(), 55, CouponType.GENERAL, "general", 20,
"msg", 10);
Coupon coupon6 = new Coupon("test6", date.startDate2(), date.endDate2(), 56, CouponType.HOME, "home", 20, "msg", 10);
Coupon coupon7 = new Coupon("test7", date.startDate3(), date.endDate3(), 95, CouponType.TRAVEL, "travel", 30.55, "msg",
10);
//	Coupon coupon8 = new Coupon("test8", date.startDate4(), date.endDate4(), 5, CouponType.HEALTH, "health", 15, "msg",
//			10);
//	Coupon coupon9 = new Coupon("test9", date.startDate1(), date.endDate1(), 5, CouponType.FURNITURE, "furn", 40, "msg",
//			10);
//	Coupon coupon10 = new Coupon("test10", date.startDate2(), date.endDate2(), 5, CouponType.HOME, "home", 10, "msg",
//			10);
//	Coupon coupon11 = new Coupon("test11", date.startDate3(), date.endDate3(), 5, CouponType.TRAVEL, "travel", 30,
//			"msg", 10);
//	Coupon coupon12 = new Coupon("test12", date.startDate4(), date.endDate4(), 5, CouponType.FOOD, "yummy", 20, "msg",
//			10);

	public class SampleDates {

		public Date startDate1() {
			java.util.Date startDate1 = new java.util.Date();
			Calendar cal = Calendar.getInstance();
			cal.set(2016, 9, 06);
			startDate1 = cal.getTime();
			return startDate1;
		}

		public Date endDate1() {
			java.util.Date endDate1 = new java.util.Date();
			Calendar cal1 = Calendar.getInstance();
			cal1.set(2018, 10, 20);
			endDate1 = cal1.getTime();
			return endDate1;
		}

		public Date startDate2() {
			java.util.Date startDate2 = new java.util.Date();
			Calendar cal2 = Calendar.getInstance();
			cal2.set(2016, 9, 30);
			startDate2 = cal2.getTime();
			return startDate2;
		}

		public Date endDate2() {
			java.util.Date endDate2 = new java.util.Date();
			Calendar cal3 = Calendar.getInstance();
			cal3.set(2018, 10, 30);
			endDate2 = cal3.getTime();
			return endDate2;
		}

		public Date startDate3() {
			java.util.Date startDate3 = new java.util.Date();
			Calendar cal4 = Calendar.getInstance();
			cal4.set(2016, 9, 26);
			startDate3 = cal4.getTime();
			return startDate3;
		}

		public Date endDate3() {
			java.util.Date endDate3 = new java.util.Date();
			Calendar cal5 = Calendar.getInstance();
			cal5.set(2018, 10, 14);
			endDate3 = cal5.getTime();
			return endDate3;
		}

		public Date startDate4() {
			java.util.Date startDate4 = new java.util.Date();
			Calendar cal7 = Calendar.getInstance();
			cal7.set(2016, 9, 10);
			startDate4 = cal7.getTime();
			return startDate4;
		}

		public Date endDate4() {
			java.util.Date endDate4 = new java.util.Date();
			Calendar cal6 = Calendar.getInstance();
			cal6.set(2018, 10, 28);
			endDate4 = cal6.getTime();
			return endDate4;

		}

	}
}
