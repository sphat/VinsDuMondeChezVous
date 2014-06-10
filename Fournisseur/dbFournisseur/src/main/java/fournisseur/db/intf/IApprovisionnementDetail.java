package fournisseur.db.intf;


public interface IApprovisionnementDetail {
	Long getId();
	
	IWineStock getWineId();
	
	int getQuantity();
	
	IApprovisionnement getApprovisionnement();
}
