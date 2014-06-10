package vin.service.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import vin.dal.db.intf.DAOException;
import vin.dal.db.intf.IWine;
import vin.dal.db.model.Wine;

public class GestionVinsBean extends VinBean {
	public List<Wine> getListeVins()
	{
		List<Wine> listeWine = new ArrayList<Wine> ();
		Collection<IWine> iWineListe = wineDao.getAllWines();
		for(IWine wine : iWineListe){
			listeWine.add((Wine) wine);
		}
		 return listeWine;
	}
	
	public Wine getVin(String fbId) throws DAOException
	{
		return wineDao.findWineById(fbId);
	}
}
