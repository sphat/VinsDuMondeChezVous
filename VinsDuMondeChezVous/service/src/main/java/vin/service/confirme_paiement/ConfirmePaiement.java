package vin.service.confirme_paiement;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import vin.service.Article;
import vin.service.MessageTemplate;
import vin.service.bean.BeanAndDAO;
import vin.service.exception.CommandeInconnueException;
import vin.service.exception.NonDisponibleException;

/**
 * Valide une commande précédement effectuée, ce qui supprime ou réserve les
 * bouteilles commandées, et renvoie une date de livraison.
 * 
 * @param commandeId
 *            l'identifiant de la commande concernée.
 * @return la date de livraison prévue.
 * @throws CommandeInconnueException
 *             si l'identifiant fourni ne correspond à aucune commande.
 * @throws NonDisponibleException
 *             si le stock de bouteilles n'est plus suffisant pour un
 *             certain vin.
 * ex: utilisation 
 * http://localhost:8080/service/rest/paiement/confirme/CMD-1
 */
@Component
@Path("/paiement")
public class ConfirmePaiement extends BeanAndDAO implements IConfirmePaiement {
	
	@SuppressWarnings("deprecation")
	@GET
	@Path("/confirme/{cmdid}")
	public ReponsePaiement confirmePaiement(@PathParam("cmdid") String commandeId) throws CommandeInconnueException, NumberFormatException, NonDisponibleException {
		
		JPAWineDAO dao = gestionVinsBean.getWineDao();
		JPAMessageDAO msgDao = gestionMessageBean.getMessageDao();
		ReponsePaiement rePaie = new ReponsePaiement();
		
		try {
			Map<String, Object> cmdConfirme = new HashMap<String, Object>();
			cmdConfirme = dao.confirmeCommande(commandeId);

			if (cmdConfirme.get(JPAWineDAO._INTROUVABLE) != null) {
				throw new CommandeInconnueException(cmdConfirme.get(
						JPAWineDAO._INTROUVABLE).toString());
			}

			if (cmdConfirme.get(JPAWineDAO._INSUFFISANT) != null) {
				@SuppressWarnings("unchecked")
				List<Object> lstArt = (List<Object>) cmdConfirme
						.get(JPAWineDAO._INSUFFISANT);
				throw new NonDisponibleException(
						new Article(lstArt.get(0).toString(),
								Integer.parseInt(lstArt.get(1).toString())));
			}

			if (cmdConfirme.get(JPAWineDAO._LIVRE_DATE) != null) {
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
				msgObj.setTitreMessage(MessageTemplate.MSG_TITRE_CONFIRME_PAIEMENT);
				msgObj.setContenuMessage(MessageTemplate.get_msg_confirme_paiement(cli.getNom(), commandeId, cmdDetail.get(JPAWineDAO.CMD_DETAIL_TOTAL_PRICE).toString()+" &euro;" ));
				msgDao.createMessage(msgObj);
								
				//Génerer la date de livraison
				//Date date_lvr = (Date) cmdConfirme.get(JPAWineDAO._LIVRE_DATE);
				//return date_lvr.toGMTString();
				
				rePaie.setOk(true);
				return rePaie;
			}
		} catch (DAOException e) {
			rePaie.setOk(false);
			e.printStackTrace();
		}
		
		
		//return commandeId;
		return rePaie;
	}
}
