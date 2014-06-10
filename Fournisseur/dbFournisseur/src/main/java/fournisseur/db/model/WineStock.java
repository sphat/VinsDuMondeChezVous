package fournisseur.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import fournisseur.db.intf.IWineStock;
@XmlRootElement
@Entity
public class WineStock implements IWineStock{

	
	@Id
	@XmlAttribute
	private String wineid;

	private double nom;
	
	public WineStock(){}
	public WineStock(String wineid, double nom){
		this.wineid = wineid;
		this.nom = nom;
	}
	@Override
	public String getWineId() {
		// TODO Auto-generated method stub
		return wineid;
	}

	@Override
	
	public double getNom() {
		// TODO Auto-generated method stub
		return nom;
	}


	public void setWineid(String wineid) {
		this.wineid = wineid;
	}

	public void setNom(double nom) {
		this.nom = nom;
	}
	
	
}
