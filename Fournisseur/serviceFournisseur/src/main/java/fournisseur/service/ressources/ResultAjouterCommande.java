package fournisseur.service.ressources;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="resultAjouterCommande")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultAjouterCommande {

	@XmlElement(name="dateLivraison")
	private Date dateLivraison;
	
	@XmlElement(name="nomMagasin")
	private String nomMagasin;
	
	@XmlElement(name="status")
	private String status;
	
	public Date getDateLivraison() {
		return dateLivraison;
	}
	public void setDateLivraison(Date dateLivraison) {
		this.dateLivraison = dateLivraison;
	}
	public String getNomMagasin() {
		return nomMagasin;
	}
	public void setNomMagasin(String nomMagasin) {
		this.nomMagasin = nomMagasin;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
