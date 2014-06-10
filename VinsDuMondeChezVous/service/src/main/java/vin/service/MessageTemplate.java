package vin.service;

public class MessageTemplate {
	
	public static final String MSG_TITRE_CONFIRME_LA_COMMANDE = "Votre commande est en cours de traitement";
	public static final String MSG_TITRE_COMMANDE_INFO = "Votre commande chez VinsDuMondeChezVous";
	public static final String MSG_TITRE_CONFIRME_LIV_DATE = "Votre commande vient d'être expédiée";
	public static final String MSG_TITRE_CONFIRME_PAIEMENT = "Confirmation de commande VinsDuMondeChezVous";
	public static final String VIN_DU_MONDE_CHEZ_VOUS = "<br /><br />L'équipe VinsDuMondeChezVous.com vous remercie pour votre confiance.<br /> A bientôt sur VinsDuMondeChezVous.com !";
	
	public static String get_msg_confirme_la_commande(String cli_nom, String cmd_id){
		String msg = "Bonjour "+cli_nom+",<br /><br />"+"Je vous confirmer que votre commande numéro "+cmd_id+" a bien enregistrée"+VIN_DU_MONDE_CHEZ_VOUS ;
		return msg;
	}
	
	public static String get_msg_commande_info(String cli_nom, String cmd_info){
		String msg = "Bonjour "+cli_nom+",<br /><br />"+"Vous venez de commander : <br />Numéro de la commande : "+cmd_info+"<br />Montant total"+VIN_DU_MONDE_CHEZ_VOUS+" &euro;";
		return msg;
	}
	
	public static String get_msg_confirme_liv_date(String cli_nom, String lvr_date){
		String msg = "Bonjour "+cli_nom+",<br /><br />"+"Nous avons le plaisir de vous annoncer que votre commande vient de quitter nos entrepôts, livraison date : "+lvr_date+VIN_DU_MONDE_CHEZ_VOUS;
		return msg;
	}
	
	public static String get_msg_confirme_paiement(String cli_nom, String cmd_id, String cmd_montant){
		String msg = "Bonjour "+cli_nom+",<br /><br />"+"Vous venez de payer votre commande :	<br />"+"Commande numéro : "+cmd_id+"<br />"+"Montant total : "+cmd_montant+VIN_DU_MONDE_CHEZ_VOUS;
		return msg;
	}
}