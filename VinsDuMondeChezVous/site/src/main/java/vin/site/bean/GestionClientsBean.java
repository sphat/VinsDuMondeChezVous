package vin.site.bean;

import vin.dal.db.intf.DAOException;
import vin.dal.db.model.Client;

public class GestionClientsBean extends ClientBean{

	public Boolean AjouterUnCient(String email, double compte, String mdp, String nom) throws DAOException{
		Client client = new Client();
		client.setEmail(email);
		client.setCompte(compte);
		client.setMdp(mdp);
		client.setNom(nom);
		if(clientDao.findClientByEmail(email)==null)
			clientDao.insertOrUpdate(client);
		else
			return false;
		
		return true;
	}
	
	public Boolean Anthentification(String email, String password) throws DAOException{
		if(clientDao.Authentification(email, password)!=null)
		return true;
		else
			return false;
	}
	
	public Client getUtilisateur(String email) throws DAOException{
		return (Client)clientDao.findClientByEmail(email);
		
	}
}
