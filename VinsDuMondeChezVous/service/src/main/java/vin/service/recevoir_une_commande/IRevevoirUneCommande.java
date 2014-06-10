package vin.service.recevoir_une_commande;

import vin.dal.db.intf.DAOException;

public interface IRevevoirUneCommande {
	public ReponseRecevoirUneCommande cmdRecevoir(String commandeId) throws DAOException;
}
