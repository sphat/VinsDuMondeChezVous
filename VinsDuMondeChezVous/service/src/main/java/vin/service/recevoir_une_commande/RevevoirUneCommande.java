package vin.service.recevoir_une_commande;

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

@Component
@Path("/recevoir_cmd")
public class RevevoirUneCommande extends BeanAndDAO  implements IRevevoirUneCommande {

	/**
	 * ex: utilisation 
	 * http://localhost:8080/service/rest/recevoir_cmd/recevoir_confirme/CMD-1
	 */
	@Override
	@GET
	@Path("/recevoir_confirme/{cmdid}")
	public ReponseRecevoirUneCommande cmdRecevoir(@PathParam("cmdid") String commandeId) throws DAOException {
		JPAWineDAO dao = gestionVinsBean.getWineDao();
		JPAMessageDAO msgDao = gestionMessageBean.getMessageDao();
		ReponseRecevoirUneCommande repRecevoirCmd = new ReponseRecevoirUneCommande();
		repRecevoirCmd.setOk(true);
		
		//Envoyer un message à client (Confirme Paiement Message)
		Map<String, Object> cmdDetail = new HashMap<String, Object>();
		
		Commande cmd = dao.findCommandById(commandeId);
		cmdDetail = dao.getCommandeDetail(commandeId);
		Client cli = cmd.getClient();
		Calendar msgDate = Calendar.getInstance();
		
		//Créer message 1er Message
		Message msgObj = msgObjBean.createMsgObj();
		msgObj.setClient(cli);
		msgObj.setDateEnvoyeMessage(msgDate.getTime());
		msgObj.setTitreMessage(MessageTemplate.MSG_TITRE_COMMANDE_INFO);
		msgObj.setContenuMessage(MessageTemplate.get_msg_commande_info(cli.getNom(), cmdDetail.get(JPAWineDAO.CMD_DETAIL_CMD_ID).toString()+"<br />"+cmdDetail.get(JPAWineDAO.CMD_DETAIL_TOTAL_PRICE).toString() ));
		msgDao.createMessage(msgObj);
		
		//Créer message 2em Message
		Message msgObj1 = msgObjBean.createMsgObj();
		msgObj1.setClient(cli);
		msgObj1.setDateEnvoyeMessage(msgDate.getTime());
		msgObj1.setTitreMessage(MessageTemplate.MSG_TITRE_CONFIRME_LA_COMMANDE);
		msgObj1.setContenuMessage(MessageTemplate.get_msg_confirme_la_commande(cli.getNom(), cmdDetail.get(JPAWineDAO.CMD_DETAIL_CMD_ID).toString()));
		msgDao.createMessage(msgObj1);
		
		return repRecevoirCmd;
	}

}
