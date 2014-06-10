package fournisseur.db.intf;

import java.util.Date;

public interface IApprovisionnement {
	long getApprovisionnementID();
	
	Date getDateLivraison();
	
	EApprovisionnementStatus getStatus();
}
