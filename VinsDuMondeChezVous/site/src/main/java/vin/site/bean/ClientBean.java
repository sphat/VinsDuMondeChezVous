package vin.site.bean;

import vin.dal.db.dao.JPAClientDAO;

public abstract class ClientBean {
	protected JPAClientDAO clientDao;

	public JPAClientDAO getClientDao() {
		return clientDao;
	}

	public void setClientDao(JPAClientDAO clientDao) {
		this.clientDao = clientDao;
	}
	


}
