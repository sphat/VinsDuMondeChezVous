package vin.service.confirme_paiement;


import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

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

@Path("/paiement")
public class ConfirmePaiement  implements IConfirmePaiement {
	
	@GET
	@Path("/confirme/{cmdid}")
	public ReponsePaiement confirmePaiement(@PathParam("cmdid") String commandeId)  {
		
		ReponsePaiement rePaie = new ReponsePaiement();
		//return commandeId;
		return rePaie;
	}
}
