package fournisseur.service.beans;

import fournisseur.db.dao.FournisseurDAO;

public abstract class FournisseurBean {
	
	protected FournisseurDAO fournisseurDao;

	public FournisseurDAO getFournisseurDao() {
		return fournisseurDao;
	}

	public void setFournisseurDao(FournisseurDAO fournisseurDao) {
		this.fournisseurDao = fournisseurDao;
	}
}
 