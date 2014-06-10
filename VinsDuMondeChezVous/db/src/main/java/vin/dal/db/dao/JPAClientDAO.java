package vin.dal.db.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import vin.dal.db.intf.DAOException;
import vin.dal.db.intf.IClient;
import vin.dal.db.model.Client;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JPAClientDAO  {
	
	private static EntityManager em;
	private static EntityManagerFactory emf;
	
	public JPAClientDAO() {
	}
	
	public JPAClientDAO(EntityManager em_ref) {
		em = em_ref;
	}

	public static EntityManagerFactory getEmf() {
		return emf;
	}

	public static void setEmf(EntityManagerFactory emf) {
		JPAClientDAO.emf = emf;
		em = emf.createEntityManager();
	}

	@PersistenceContext
	public void setEntityManager(EntityManager em_ref) {
		em = em_ref;
	}

	/**
	 * Permettre de créer ou mise à jour le client existant 
	 * @param client
	 * @throws DAOException
	 */
	@Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
	public void insertOrUpdate(IClient client) throws DAOException {
		try {
			Client c = (Client) client;
			if (c.getId() > 0 && em.find(Client.class, c.getId()) != null) {
				em.merge(c);
			} else {
				em.persist(c);
			}
	          em.getTransaction().begin();
	          em.getTransaction().commit();
	         
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(e);
		}
	}

    /**
     * Chercher client par ID
     * @param clientId
     * @return
     * @throws DAOException
     */
	public IClient findClientById(int clientId) throws DAOException {
		return em.find(Client.class, clientId);
	}
	
	public IClient findClientByEmail(String clientEmail) throws DAOException {
		Client client = new Client();
		try{
			client = (Client) em
				.createQuery(
						"SELECT l FROM Client l WHERE l.email = :email")
				.setParameter("email", clientEmail).getSingleResult();
		}
		catch(NoResultException ex ){
			client = null;
			return client;
			}
		
		return client;
	}
	
	public IClient Authentification(String clientEmail,String password) throws DAOException {
		Client client = new Client();
		try{
			client = (Client) em
				.createQuery(
						"SELECT l FROM Client l WHERE l.email = :email AND l.mdp = :pass")
				.setParameter("email", clientEmail).setParameter("pass", password).getSingleResult();
		}
		catch(NoResultException ex ){
			client = null;
			return client;
			}
		
		return client;
	}
	
	

    /**
     * Permettre d'ébiter le compte client
     * @param clientID
     * @param a_debit
     * @return
     * @throws DAOException
     */
	 
	@Transactional(readOnly = false, propagation = Propagation.MANDATORY)
	public Boolean debiterCompteClient(int clientID, double a_debit)
			throws DAOException {
		// TODO Auto-generated method stub
		Client c = em.find(Client.class, clientID);
		if(c!=null){
			if(c.getCompte() >= a_debit){
				double compte_courant = c.getCompte() - a_debit;
				c.setCompte(compte_courant);
//				tx = em.getTransaction();
//				tx.begin();
				em.merge(c);
//				tx.commit();
				return true;
			}
		}
		return false;
	}
}
