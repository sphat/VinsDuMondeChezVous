package vin.service.confirme_paiement;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

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

@Component
@Path("/paiement1")
public class ConfirmePaiementReponse extends BeanAndDAO implements IConfirmePaiementReponse {

	@Override
	@POST
	@Path("/confirme1")
	@Consumes("application/xml")
	public ReponsePaiement confirmePaiement(ReponsePaiement paiementReponse)
			throws CommandeInconnueException, NonDisponibleException {
		
		JPAWineDAO dao = gestionVinsBean.getWineDao();
		JPAMessageDAO msgDao = gestionMessageBean.getMessageDao();
		ReponsePaiement rePaie = paiementReponse;
		
		try {
			Map<String, Object> cmdConfirme = new HashMap<String, Object>();
			cmdConfirme = dao.confirmeCommande(rePaie.getRaison()); //getRaison = getcmdID

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
				
				Commande cmd = dao.findCommandById(rePaie.getRaison()); //getRaison = getcmdID
				cmdDetail = dao.getCommandeDetail(rePaie.getRaison()); //getRaison = getcmdID
				Client cli = cmd.getClient();
				Calendar msgDate = Calendar.getInstance();
				
				//Créer message
				Message msgObj = msgObjBean.createMsgObj();
				msgObj.setClient(cli);
				msgObj.setDateEnvoyeMessage(msgDate.getTime());
				msgObj.setTitreMessage(MessageTemplate.MSG_TITRE_CONFIRME_PAIEMENT);
				msgObj.setContenuMessage(MessageTemplate.get_msg_confirme_paiement(cli.getNom(), rePaie.getRaison(), cmdDetail.get(JPAWineDAO.CMD_DETAIL_TOTAL_PRICE).toString()+" &euro;" ));
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
