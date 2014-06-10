package fournisseur.db.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



import fournisseur.db.intf.EApprovisionnementStatus;
import fournisseur.db.intf.IApprovisionnement;

@XmlRootElement(name="approvisionnement")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Table(name = "approvisionnement")
public class Approvisionnement implements IApprovisionnement {
	
	@Id
	@GeneratedValue
	@XmlAttribute
	long appId;
	@XmlElement(name="magazine")
	String magazine;
	
	@Temporal(TemporalType.DATE)
	@XmlElement(name="dateLivraison")
	private Date dateLivraison;
	
	
	@Enumerated(EnumType.STRING)
	@XmlElement(name="status")
	private	EApprovisionnementStatus status;	//2 statut d'un approvisionnement : Confirmé, Livré
	@XmlElement
	//, cascade = { CascadeType.ALL },orphanRemoval=true
	@OneToMany(targetEntity=ApprovisionnementDetail.class, mappedBy = "approvisionnement")
	private
	List<ApprovisionnementDetail> listDetail ;
	
	@XmlElement
	public String getMagazine(){
		return magazine;
	}
	
	public void setMagazine(String magazine){
		this.magazine = magazine;
	}
	
	public void setDateLivraison(Date dateLivraison){
		this.dateLivraison = dateLivraison;
	}
	
	public void setCmdStatus(EApprovisionnementStatus status){
		this.status = status;
	}
	
	
	@Override
	public long getApprovisionnementID() {
		return appId;
	}

	@Override
	@XmlElement
	public Date getDateLivraison() {
		return dateLivraison;
	}

	@Override
	@XmlElement
	public EApprovisionnementStatus getStatus() {
		return status;
	}

	public List<ApprovisionnementDetail> getListDetail() {
		return listDetail;
	}

	public void setListDetail(List<ApprovisionnementDetail> listDetail) {
		this.listDetail = listDetail;
	}

}
