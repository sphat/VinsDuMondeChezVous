package fournisseur.db.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import fournisseur.db.intf.EApprovisionnementStatus;
import fournisseur.db.intf.IApprovisionnement;
import fournisseur.db.intf.IApprovisionnementDetail;
import fournisseur.db.intf.IWineStock;
import fournisseur.db.model.Approvisionnement;
import fournisseur.db.model.ApprovisionnementDetail;
import fournisseur.db.model.WineStock;

public class FournisseurDAO {
	
	private EntityManagerFactory emf;

	@PersistenceContext
	private EntityManager em;
	
		public FournisseurDAO(EntityManager _em){
			em = _em;
		}
		
		public FournisseurDAO(){}

		public EntityManagerFactory getEmf() {
			return this.emf;
		}

		public void setEmf(EntityManagerFactory emf) {
			this.emf = emf;
			em = emf.createEntityManager();
		}		
	
	public String TestFonction(){
		return "Test est réussi !!!";
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public WineStock findWineStockById(String wineId) throws Exception {
		return em.find(WineStock.class, wineId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public IApprovisionnement findApprovisionnementById(long appId) throws Exception {
		return em.find(Approvisionnement.class, appId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public IApprovisionnementDetail findApprovisionnementDetailById(long id) throws Exception {
		return em.find(ApprovisionnementDetail.class, id);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void insertOrUpdate(IWineStock wine) throws Exception {
		try {
			WineStock w = (WineStock) wine;
			if (w.getWineId() != null && em.find(WineStock.class, w.getWineId()) != null) {
				em.merge(w);
			} else {
				em.persist(w);
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void insertOrUpdate(IApprovisionnement app) throws Exception {
		try {
			Approvisionnement a = (Approvisionnement) app;
			if (null != String.valueOf(a.getApprovisionnementID()) && em.find(Approvisionnement.class, a.getApprovisionnementID()) != null) {
				em.merge(a);
			} else {
				em.persist(a);
			}
			
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(e);
		}
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void insertOrUpdate(IApprovisionnementDetail detail) throws Exception {
		try {
			ApprovisionnementDetail d = (ApprovisionnementDetail) detail;
			if (null != d.getId() && em.find(ApprovisionnementDetail.class, d.getId()) != null) {
				em.merge(d);
				
			} else {
				//em.persist(d);
				em.merge(d);
			}
		} catch (ClassCastException e) {
			throw new IllegalArgumentException(e);
		}
		em.close();
	}
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public Approvisionnement checkExisteCommandeConfirme(String nomDuMagasin){
		
		Approvisionnement appro = new Approvisionnement();
		try{
			appro = (Approvisionnement) em
				.createQuery(
						"SELECT appro FROM Approvisionnement appro WHERE appro.status = :status AND appro.magazine = :magazine")
				.setParameter("status",EApprovisionnementStatus.Confirme).setParameter("magazine", nomDuMagasin).getSingleResult();
		}
		catch(NoResultException ex ){
			appro = null;
			return appro;
			}
		return appro;
	}
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<String> getListMagasinLivraison(Date date){
		List<String> Result = new ArrayList<String>();
		Result = (List<String>) em
				.createQuery(
						"SELECT appro.magazine FROM Approvisionnement appro WHERE appro.dateLivraison = :date AND appro.status = :status")
				.setParameter("date",date).setParameter("status",EApprovisionnementStatus.Confirme).getResultList();
		
		return Result;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.REQUIRED)
	public List<ApprovisionnementDetail> getListApproDetail(long approId){
		List<ApprovisionnementDetail> result = new ArrayList<ApprovisionnementDetail>();
		try{
		result = (List<ApprovisionnementDetail>) em
				.createQuery("SELECT appro FROM fournisseur.db.model.ApprovisionnementDetail appro WHERE appro.approvisionnement.appId = :approId")
				.setParameter("approId",approId).getResultList();
		}
		catch(NoResultException ex ){
			result = null;
			return result;
			}
		return result;
	}
	
	
	
	
	
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean Livraison(String nomDuMagasin) throws Exception{
		boolean result = false;
		Approvisionnement appro = new Approvisionnement();
		try{
			appro = (Approvisionnement) em
				.createQuery(
						"SELECT appro FROM Approvisionnement appro WHERE appro.status = :status AND appro.magazine = :magazine")
				.setParameter("status",EApprovisionnementStatus.Confirme).setParameter("magazine", nomDuMagasin).getSingleResult();
		}
		catch(NoResultException ex ){
			appro = null;
			}
		if(appro!=null){
			appro.setCmdStatus(EApprovisionnementStatus.Livre);
			System.out.println(appro.getStatus());
			insertOrUpdate(appro);
			result = true;
		}

		
			
		return result;
	}
	
	 
}
