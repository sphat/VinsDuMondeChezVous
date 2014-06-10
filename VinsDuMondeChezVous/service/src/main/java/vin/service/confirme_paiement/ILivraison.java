package vin.service.confirme_paiement;

import vin.dal.db.intf.DAOException;
import vin.service.exception.CommandeInconnueException;

public interface ILivraison {
	public ReponseLivraison livraisonEffectuee(String commandeId) throws DAOException, CommandeInconnueException;
}
