package vin.site.bean;

import vin.dal.db.dao.JPAMessageDAO;

public abstract class MessageBean {
	protected JPAMessageDAO messageDao;

	public JPAMessageDAO getMessageDao() {
		return messageDao;
	}

	public void setMessageDao(JPAMessageDAO messageDao) {
		this.messageDao = messageDao;
	}
}
