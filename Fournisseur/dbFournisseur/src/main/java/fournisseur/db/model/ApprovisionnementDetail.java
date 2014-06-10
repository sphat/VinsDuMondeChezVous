package fournisseur.db.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import fournisseur.db.intf.IApprovisionnement;
import fournisseur.db.intf.IApprovisionnementDetail;

@XmlRootElement
@Entity
@Table(name = "approvisionnementdetail")
public class ApprovisionnementDetail implements IApprovisionnementDetail{

	@Id
	@GeneratedValue()
	long id;
	
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "WineStock", referencedColumnName="wineid")
	private
	WineStock wineId;
	
	private int quantity;
	@ManyToOne(cascade= CascadeType.ALL)
	@JoinColumn(name = "Approvisionnement", referencedColumnName="appId")
	private
	Approvisionnement approvisionnement;
	
	public ApprovisionnementDetail(){}
	
		
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public WineStock getWineId() {
		return wineId;
	}

	@Override
	public int getQuantity() {
		return quantity;
	}

	@Override
	public IApprovisionnement getApprovisionnement() {
		return approvisionnement;
	}
	
	
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void setWineId(WineStock wineId) {
		this.wineId = wineId;
	}
	
	public void setApprovisionnement(Approvisionnement approvisionnement) {
		this.approvisionnement = approvisionnement;
	}

}
