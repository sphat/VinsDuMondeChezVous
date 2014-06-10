package vin.service.confirme_paiement;

import vin.service.exception.CommandeInconnueException;
import vin.service.exception.NonDisponibleException;

public interface IConfirmePaiementReponse {
	public ReponsePaiement confirmePaiement(ReponsePaiement paiementReponse) throws CommandeInconnueException,NonDisponibleException;
}
