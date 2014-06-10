package vin.service.confirme_paiement;

import vin.service.exception.CommandeInconnueException;
import vin.service.exception.NonDisponibleException;

public interface IConfirmePaiement {
	public ReponsePaiement confirmePaiement(String commandeId) throws CommandeInconnueException,NonDisponibleException;
}
