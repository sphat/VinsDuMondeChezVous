package vin.service.bean;

import vin.dal.db.dao.JPAWineDAO;

public class VinBean {
	protected JPAWineDAO wineDao;

	public JPAWineDAO getWineDao() {
		return wineDao;
	}

	public void setWineDao(JPAWineDAO wineDao) {
		this.wineDao = wineDao;
	}
}
