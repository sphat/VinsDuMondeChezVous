package vin.dal.db.intf;

import vin.dal.db.model.Commande;
import vin.dal.db.model.Wine;

public interface ICommandeArticle {
	Long getId();
	
	Wine getWine();
	
	int getQuantity();
	
	Commande getCommande();
}
