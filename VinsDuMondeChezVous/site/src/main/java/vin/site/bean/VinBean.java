package vin.site.bean;

import vin.dal.db.dao.JPAWineDAO;

public abstract class VinBean {
	protected JPAWineDAO wineDao;

	public JPAWineDAO getWineDao() {
		return wineDao;
	}

	public void setWineDao(JPAWineDAO wineDao) {
		this.wineDao = wineDao;
	}
}
