package vin.service.recevoir_une_commande;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/recevoir_cmd")
public interface IRevevoirUneCommande {
	@GET
	@Path("/recevoir_confirme/{cmdid}")
	public ReponseRecevoirUneCommande cmdRecevoir(String commandeId);
}
