package fournisseur.service.ressources;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fournisseur.db.intf.EApprovisionnementStatus;
import fournisseur.db.model.Approvisionnement;
import fournisseur.db.model.ApprovisionnementDetail;
import fournisseur.db.model.WineStock;
import fournisseur.service.beans.FournisseurBean;
import fournisseur.service.beans.GestionStock;

@Component
@Path("/Demande")
public class RApprovisionnements extends FournisseurBean{
	
	@Autowired
	public GestionStock gestionStock ;
	
	@GET
	@Path("/TestFonction/{stockID}")
	public WineStock getApprovisionnementListWB(@PathParam("stockID") String stockID) throws Exception {
		return gestionStock.GetWineStock(stockID);
	}
	
	@GET
	@Path("/Livraison/{date}")
	@Consumes("application/xml")
	public ReSultServiceLivraison Livraison(@PathParam("date")DateParam  dateParam) throws Exception {
		Date date = dateParam.getDateParam();
		ReSultServiceLivraison resultLivraison = new ReSultServiceLivraison();
		List<String> nomDuMagasins = new ArrayList<String>();
		nomDuMagasins = gestionStock.getListMagasinLivraison(date);
		Approvisionnement appro = new Approvisionnement();
		
		if(nomDuMagasins.size()!=0){
			resultLivraison.setResult("true");
			for(int j=0;j<nomDuMagasins.size();j++){
			appro = gestionStock.checkExisteCommandeConfirme(nomDuMagasins.get(j));
			boolean result = gestionStock.Livraison(nomDuMagasins.get(j));
			if(result){
				
				List<ApprovisionnementDetail> listApproDetail = new ArrayList<ApprovisionnementDetail>();
				listApproDetail = gestionStock.getListApproDetail(appro.getApprovisionnementID());
				for(int i=0;i<listApproDetail.size();i++){
					WineStock winstock = gestionStock.GetWineStock(listApproDetail.get(i).getWineId().getWineId());
					double nouveauQuantite = winstock.getNom() - listApproDetail.get(i).getQuantity();
					winstock.setNom(nouveauQuantite);
					gestionStock.insertOrUpdateWine(winstock);
				}
			}
			}
			
		}
		else
			resultLivraison.setResult("false");
		
	    return resultLivraison;
	  }
	
	
	/* les parametre pour tester avec le SoapUI
	<parametre>
	
	<listArcticle>
		<article>
		     <reference>CruRouge2er</reference>
			<quantite>1</quantite>
		</article>
		<article>
		     <reference>CruRouge</reference>
			<quantite>10</quantite>
		</article>
	</listArcticle>
	
	<nomDuMasin>VinDuThangChezVous</nomDuMasin>
	</parametre>
	*/
	// - chercher dans la base de donnees la commande de ce magasin qui a le status est confirme.
	// - si oui: ajouter tous les vins dans la commande trouvé.
	// - si non: creer un nouveau commande.	  
	@POST
	@Path("/AjouterUneCommande")  
	@Consumes("application/xml")
	public ResultAjouterCommande AjouterUneCommande(ParametreAjouterCommande parametre ) throws Exception {
		
		Article[] listWineDemande = parametre.getArticle();
		ResultAjouterCommande result = new ResultAjouterCommande();
		String nomDuMagasin = parametre.getNomDuMasin();
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 2);
		Date dateLivraison = calendar.getTime(); 		
		
		Approvisionnement appro = new Approvisionnement();
		appro = gestionStock.checkExisteCommandeConfirme(nomDuMagasin);
		
		List<ApprovisionnementDetail> listApproDetail = new ArrayList<ApprovisionnementDetail>();
	    
	    
		if(appro==null){
			appro = new Approvisionnement();
			appro.setCmdStatus(EApprovisionnementStatus.Confirme);
		    appro.setDateLivraison(null);
		    appro.setMagazine(nomDuMagasin);
			for(int i=0;i<listWineDemande.length;i++){
		    	WineStock wineStock = gestionStock.GetWineStock(listWineDemande[i].getReferenceVin().toString());
		    	int quantite = listWineDemande[i].getNombre();
		    	ApprovisionnementDetail appDetail = new ApprovisionnementDetail();
		    	appDetail.setApprovisionnement(appro);
		    	appDetail.setQuantity(quantite);
		    	appDetail.setWineId(wineStock);
		    	listApproDetail.add(appDetail);
		    }
			appro.setDateLivraison(dateLivraison);
		    gestionStock.insertApprovisionnement(appro);
		    for(int i=0;i<listApproDetail.size();i++){
		    	gestionStock.InsertOrUpdateApprovisionementDeatail(listApproDetail.get(i));
		    }
			
		}
		else{
			
			listApproDetail = gestionStock.getListApproDetail(appro.getApprovisionnementID());
			
			for(int j=0;j<listWineDemande.length;j++){
		    	
		    	String idWine = listWineDemande[j].getReferenceVin().toString();
		    	int quantite = listWineDemande[j].getNombre();
		    	boolean etreDansDemande = false;
		    	
		    	for(int i = 0; i< listApproDetail.size();i++){
		    		String idWineDansList = listApproDetail.get(i).getWineId().getWineId();
		    		if(idWineDansList.equals(idWine)){
		    			int nouveauQuantite = quantite + listApproDetail.get(i).getQuantity();
		    			listApproDetail.get(i).setQuantity(nouveauQuantite);
		    			etreDansDemande = true;
		    		}
		    	
		    	}
		    	
		    	if(etreDansDemande==false){
		    	WineStock wineStock = gestionStock.GetWineStock(listWineDemande[j].getReferenceVin().toString());
    			Approvisionnement approTemps = gestionStock.getApproById(appro.getApprovisionnementID());
		    	ApprovisionnementDetail appDetail = new ApprovisionnementDetail();
		    	appDetail.setApprovisionnement(approTemps);
		    	appDetail.setQuantity(quantite);
		    	appDetail.setWineId(wineStock);
    			gestionStock.InsertOrUpdateApprovisionementDeatail(appDetail);
		    	}
			}
			
			for(int i=0;i<listApproDetail.size();i++)
				gestionStock.InsertOrUpdateApprovisionementDeatail(listApproDetail.get(i));
		}
		
		result.setDateLivraison(appro.getDateLivraison());
		result.setNomMagasin(appro.getMagazine());
		result.setStatus(appro.getStatus().toString());
		return result;
		
	  }

	
}
