package vin.site.commande_api;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import vin.dal.db.dao.JPAClientDAO;
import vin.dal.db.dao.JPAWineDAO;
import vin.dal.db.intf.DAOException;
import vin.dal.db.model.Client;
import vin.dal.db.model.Wine;
import vin.site.bean.GestionVinsBean;
import vin.site.commande_intf.Icommande;
import vin.site.error_notification.CommandeInconnueException;
import vin.site.error_notification.NonDisponibleException;

public class Commande extends GestionVinsBean implements Icommande, ApplicationContextAware {

	private ApplicationContext applicationContext;
	private JPAWineDAO wineDao;
	private JPAClientDAO clientDao;
	
	//@XmlElement(name="vin")
	private Article[] article;
	
	public Article[] getArticle() {
		return article;
	}
	
	public JPAWineDAO getJPAWineDAOBean(){
		return this.applicationContext.getBean("wineDao", JPAWineDAO.class);		
	}
	
	public JPAClientDAO getJPAClientDAOBean(){
		return this.applicationContext.getBean("clientDao", JPAClientDAO.class);
	}

	/**
	 * Créée une commande en fonction de la liste des vins demandée, et renvoie
	 * l'identifiant de la commande, ainsi que le prix.
	 * 
	 * Algo 1.Créer un nouvelle commande(date_livraison=null,
	 * commande_status_ouverte). 2.Bougler dans Objet vins(VinID, VinQTY) et
	 * créer commande_article
	 * 
	 * @param vins
	 *            la liste des vins commandée
	 * @return un objet contenant l'identifiant de la commande, ainsi que le
	 *         prix de cette commande.
	 * @throws NonDisponibleException
	 *             si le nombre de bouteilles disponibles pour un certain vin
	 *             est insuffisant.
	 */
	public CommandeInfos commande(Article[] vins) throws NonDisponibleException {
		try {
			wineDao = getJPAWineDAOBean();
			clientDao = getJPAClientDAOBean();
			Client cli = (Client) clientDao.findClientById(Integer.parseInt(UserSession.session.getAttribute(UserSession.SESSION_USER_ID).toString()));
			
			Map<String, Integer> cmdArt = new HashMap<String, Integer>();
			// Vérifier si le stock est suffisant pour la commande
			for (Article v_tmp : vins) {
				Wine objV = wineDao.findWineById(v_tmp.getReferenceVin());
				if(objV == null) throw new NonDisponibleException(v_tmp);
				if (objV.getBottles().size() < v_tmp.getNombre()) {
					throw new NonDisponibleException(v_tmp);
				}
				// Construire map pour la méthode dao.createCommande
				cmdArt.put(v_tmp.getReferenceVin(), v_tmp.getNombre());
			}
			List<Object> cmdInfo = wineDao.createCommande(cmdArt, cli);
			return new CommandeInfos(Double.parseDouble(cmdInfo.get(1)
					.toString()), cmdInfo.get(0).toString());
		} catch (DAOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Date confirmeCommande(String commandeId)
			throws CommandeInconnueException, NonDisponibleException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean livraisonEffectuee(String commandeId)
			throws CommandeInconnueException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
		
	}

}
