package vin.dal.db.dao;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import vin.dal.db.intf.DAOException;
import vin.dal.db.intf.IBottle;
import vin.dal.db.intf.IClient;
import vin.dal.db.intf.IMessage;
import vin.dal.db.model.Client;
import vin.dal.db.model.Message;

@Component
public class JPAMessageDAO {
	
	private static EntityManager em;
	private static EntityManagerFactory emf;
	
	public JPAMessageDAO() {
	}
	
	public JPAMessageDAO(EntityManager em_ref) {
		em = em_ref;
	}

	public static EntityManagerFactory getEmf() {
		return emf;
	}

	public static void setEmf(EntityManagerFactory emf) {
		JPAMessageDAO.emf = emf;
		em = emf.createEntityManager();
	}

	@PersistenceContext
	public void setEntityManager(EntityManager em_ref) {
		em = em_ref;
	}
	
	@SuppressWarnings("unchecked")
	 
	public Collection<IMessage> getAllMessages() {
		return new ArrayList<IMessage>(em.createQuery("SELECT m FROM Message m")
				.getResultList());
	}
	
	@SuppressWarnings("unchecked")
	public Collection<IMessage> getAllMessagesByIdClient(int idClient) {
		
		return new ArrayList<IMessage>(em.createQuery("SELECT m FROM Message m WHERE m.client.id = :idclient")
				.setParameter("idclient", idClient).getResultList());
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void createMessage(IMessage msg_){
		Message msg = (Message) msg_;
		if (msg.getId() != null) {
			em.merge(msg);
		} else {
			em.persist(msg);
		}
	}

}
