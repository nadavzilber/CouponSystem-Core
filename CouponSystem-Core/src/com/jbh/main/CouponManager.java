package com.jbh.main;

import com.jbh.tasks.DailyCleaningTask;
import com.jbh.facade.AdminFacade;
import com.jbh.facade.CompanyFacade;
import com.jbh.facade.CouponClientFacade;
import com.jbh.facade.CustomerFacade;
import com.jbh.utils.ConnectionPool;
import com.jbh.enums.ClientType;

public class CouponManager {

	private DailyCleaningTask dailyClean;
	SampleData sampleObjects = new SampleData();

	public DailyCleaningTask getDailyTask() {
		return dailyClean;
	}

	public void setDailyTask(DailyCleaningTask dailyTask) {
		this.dailyClean = dailyTask;
	}

	private CouponManager() {
		new Thread(dailyClean).start();
	}

	private static CouponManager instance = null;

	public static CouponManager getInstance() {
		if (instance == null) {
			synchronized (CouponManager.class) {
				if (instance == null) {
					instance = new CouponManager();
				}
			}
		}
		return instance;
	}

	public CouponClientFacade login(String name, String password, ClientType clientType) throws Exception {
	
		AdminFacade adminfacade = new AdminFacade();
		CompanyFacade companyfacade = new CompanyFacade();
		CustomerFacade customerfacade = new CustomerFacade();

		switch (clientType) {
		case ADMIN:
			adminfacade = (AdminFacade) adminfacade.login(name, password);
			return adminfacade;

		case COMPANY:
			companyfacade = (CompanyFacade) companyfacade.login(name, password);
			return companyfacade;

		case CUSTOMER:
			customerfacade = (CustomerFacade) customerfacade.login(name, password);
			return customerfacade;

		default:
			return null;

		}

	}
	public void shutdown() {
		dailyClean.setActive(false);
		ConnectionPool.getInstance().closeAllConnections();
	}
}
