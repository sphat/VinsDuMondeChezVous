package vin.service.confirme_paiement;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.springframework.stereotype.Component;

import vin.dal.db.dao.JPAMessageDAO;
import vin.dal.db.dao.JPAWineDAO;
import vin.dal.db.intf.DAOException;
import vin.dal.db.model.Client;
import vin.dal.db.model.Commande;
import vin.dal.db.model.Message;
import vin.service.MessageTemplate;
import vin.service.bean.BeanAndDAO;
import vin.service.exception.CommandeInconnueException;

@Component
@Path("/livraison")
public class Livraison extends BeanAndDAO implements ILivraison{

	/**
	 * Confirme la bonne livraison de la commande.
	 * 
	 * @param commandeId
	 *            l'identifiant de la commande concernée.
	 * @return false si la commande avait déjà été confirmée.
	 * @throws CommandeInconnueException
	 *             si l'identifiant fourni ne correspond à aucune commande.
	 * ex: http://localhost:8080/service/rest/livraison/confirme/CMD-1
	 */
	@Override
	@GET
	@Path("/confirme/{cmdid}")
	public ReponseLivraison livraisonEffectuee(@PathParam("cmdid") String commandeId) throws DAOException, CommandeInconnueException{
		JPAWineDAO dao = gestionVinsBean.getWineDao();
		JPAMessageDAO msgDao = gestionMessageBean.getMessageDao();
		ReponseLivraison repLivr = new ReponseLivraison();
		repLivr.setOk(true);
		repLivr.setRaison("OK");
		
			Map<String, Object> cmdLvrEffec = new HashMap<String, Object>();
			cmdLvrEffec = dao.livraisonEffectuee(commandeId);
			
			if (cmdLvrEffec.get(JPAWineDAO._INTROUVABLE) != null) {
				repLivr.setOk(false);
				repLivr.setRaison("Commande "+JPAWineDAO._INTROUVABLE);
				throw new CommandeInconnueException(cmdLvrEffec.get(JPAWineDAO._INTROUVABLE).toString());
			}
			
			//Envoyer un message à client (Confirme Paiement Message)
			Map<String, Object> cmdDetail = new HashMap<String, Object>();
			Commande cmd = dao.findCommandById(commandeId);
			cmdDetail = dao.getCommandeDetail(commandeId);
			
			Client cli = cmd.getClient();
			Calendar msgDate = Calendar.getInstance();
			
			//Créer message
			Message msgObj = msgObjBean.createMsgObj();
			msgObj.setClient(cli);
			msgObj.setDateEnvoyeMessage(msgDate.getTime());
			msgObj.setTitreMessage(MessageTemplate.MSG_TITRE_CONFIRME_LIV_DATE);
			msgObj.setContenuMessage(MessageTemplate.get_msg_confirme_liv_date(cli.getNom(), cmdDetail.get(JPAWineDAO.CMD_DETAIL_LIV_DATE).toString()));
			msgDao.createMessage(msgObj);
			
			return repLivr;
			
	}

}
