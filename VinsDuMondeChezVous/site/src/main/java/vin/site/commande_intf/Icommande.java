package vin.site.commande_intf;

import java.util.Date;

import vin.site.commande_api.Article;
import vin.site.commande_api.CommandeInfos;
import vin.site.error_notification.CommandeInconnueException;
import vin.site.error_notification.NonDisponibleException;

public interface Icommande {
	/**
	 * Créée une commande en fonction de la liste des vins demandée, et renvoie
	 * l'identifiant de la commande, ainsi que le prix.
	 * 
	 * @param vins
	 *            la liste des vins commandée
	 * @return un objet contenant l'identifiant de la commande, ainsi que le
	 *         prix de cette commande.
	 * @throws NonDisponibleException
	 *             si le nombre de bouteilles disponibles pour un certain vin
	 *             est insuffisant.
	 */
	CommandeInfos commande(Article[] vins) throws NonDisponibleException;

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
	 */

	Date confirmeCommande(String commandeId) throws CommandeInconnueException, NonDisponibleException;

	/**
	 * Confirme la bonne livraison de la commande.
	 * 
	 * @param commandeId
	 *            l'identifiant de la commande concernée.
	 * @return false si la commande avait déjà été confirmée.
	 * @throws CommandeInconnueException
	 *             si l'identifiant fourni ne correspond à aucune commande.
	 */
	boolean livraisonEffectuee(String commandeId) throws CommandeInconnueException;
}
