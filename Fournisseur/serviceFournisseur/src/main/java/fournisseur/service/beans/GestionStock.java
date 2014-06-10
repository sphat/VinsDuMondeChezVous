package fournisseur.service.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import fournisseur.db.dao.FournisseurDAO;
import fournisseur.db.model.Approvisionnement;
import fournisseur.db.model.ApprovisionnementDetail;
import fournisseur.db.model.WineStock;


public class GestionStock{
	
	protected FournisseurDAO fournisseurDao;

	public FournisseurDAO getFournisseurDao() {
		return fournisseurDao;
	}

	public void setFournisseurDao(FournisseurDAO fournisseurDao) {
		this.fournisseurDao = fournisseurDao;
	}
	
	public WineStock GetWineStock(String stockID) throws Exception
	{
		WineStock wineStock = fournisseurDao.findWineStockById(stockID);
		return wineStock;
	}
	
	public Approvisionnement getApproById(long id) throws Exception{
		return (Approvisionnement) fournisseurDao.findApprovisionnementById(id);
	}
	
	public void insertApprovisionnement(Approvisionnement appro) throws Exception{
		fournisseurDao.insertOrUpdate(appro);
	}
	
	public void insertOrUpdateWine(WineStock wine) throws Exception{
		fournisseurDao.insertOrUpdate(wine);
	}
	
	public void updateApprovisionnement(Approvisionnement appro) throws Exception{
		fournisseurDao.insertOrUpdate(appro);
	}
	
	public void InsertOrUpdateApprovisionementDeatail(ApprovisionnementDetail detail) throws Exception{
		fournisseurDao.insertOrUpdate(detail);
	}
	
	public Approvisionnement checkExisteCommandeConfirme(String nomDuMagasin){
		Approvisionnement appro = new Approvisionnement();
		appro = fournisseurDao.checkExisteCommandeConfirme(nomDuMagasin);
		return appro;
	}
	
	public List<String> getListMagasinLivraison(Date date){
		List<String> result = new ArrayList<String>();
		result = fournisseurDao.getListMagasinLivraison(date);
		return result;
	}
	
	public boolean Livraison(String nomDuMagasin) throws Exception{
		return fournisseurDao.Livraison(nomDuMagasin);
	}
	
	public List<ApprovisionnementDetail> getListApproDetail(long approid){
		List<ApprovisionnementDetail> result = new ArrayList<ApprovisionnementDetail>();
		result = fournisseurDao.getListApproDetail(approid);
		return result;
	}
	
	public String SayHello()
	{
		return "Hello!!!";
	}
}
