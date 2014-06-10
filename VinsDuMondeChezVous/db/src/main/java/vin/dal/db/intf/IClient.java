package vin.dal.db.intf;

public interface IClient {
	
	/**
	 * L'identifiant du client.
	 */
	int getId();
	
	/**
	 * Le nom du client.
	 */
	String getNom();

	/**
	 * La quantité d'argent que le client possède sur son compte de location.
	 */
	double getCompte();
	
	/**
	 * @return Email adresse du client
	 */
	String getEmail();
	
	/**
	 * @return le mot de passe de client, Crypter
	 */
	String getMdp();
}
