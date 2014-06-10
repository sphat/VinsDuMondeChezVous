package vin.dal.db.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import vin.dal.db.intf.ICommande;

@Entity
@Table(name = "commande")
public class Commande implements ICommande, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * id, primary key de la table 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", columnDefinition = "bigint")
	private Long id;
	
	/**
	 * cmdID, commande ID, pour communiquer avec le client
	 */
	@Column(name = "cmd_id", columnDefinition = "character(15)")
	private String cmdID;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_livraison")
	private Date dateLivraison;
	
	@Column(name = "commande_status", columnDefinition = "character(255)")	
	private String commandeStatus;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_Commande")
	private Date dateCommande;
	
	@ManyToOne
	private Client client;
	
	@OneToMany(mappedBy = "commande")
	private List<CommandeArticle> commandArticles = new ArrayList<CommandeArticle>();

	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Date dateLivraison() {
		return this.dateLivraison;
	}

	public String commandeStatus() {
		return this.commandeStatus;
	}

	public Date getDateLivraison() {
		return dateLivraison;
	}

	public void setDateLivraison(Date dateLivraison) {
		this.dateLivraison = dateLivraison;
	}

	public String getCommandeStatus() {
		return commandeStatus;
	}

	public void setCommandeStatus(String commandeStatus) {
		this.commandeStatus = commandeStatus;
	}

	public List<CommandeArticle> getCommandArticles() {
		return commandArticles;
	}

	public void setCommandArticles(List<CommandeArticle> commandArticles) {
		this.commandArticles = commandArticles;
	}

	public String getCmdID() {
		return cmdID;
	}

	public void setCmdID(String cmdID) {
		this.cmdID = cmdID;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	@Override
	public Date dateCommande() {
		return this.dateCommande;
	}
	
}
