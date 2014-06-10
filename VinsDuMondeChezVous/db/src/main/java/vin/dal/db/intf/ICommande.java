package vin.dal.db.intf;

import java.util.Date;

public interface ICommande {
	Long getId();
	
	/**
	 * cmdID, commande ID, pour communiquer avec le client
	 */
	String getCmdID();
	
	Date dateLivraison();
	
	String commandeStatus();
	
	Date dateCommande();
}
