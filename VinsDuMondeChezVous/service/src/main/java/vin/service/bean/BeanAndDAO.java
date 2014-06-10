package vin.service.bean;

import org.springframework.beans.factory.annotation.Autowired;

import vin.dal.db.dao.JPAClientDAO;

public class BeanAndDAO {
	@Autowired
	public GestionVinsBean gestionVinsBean;
	
	@Autowired
	public GestionMessageBean gestionMessageBean;
	
	@Autowired
	public JPAClientDAO cliDao;
	
	@Autowired
	public MessageObj msgObjBean;
}
