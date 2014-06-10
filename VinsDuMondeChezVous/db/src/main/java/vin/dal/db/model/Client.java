package vin.dal.db.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import vin.dal.db.intf.IClient;

@Entity
@Table(name = "client")
@XmlRootElement(name = "client", namespace="http://www.univ-lyon1.fr/M2TI/TIW5/wine/service/livraisons/")
@XmlAccessorType(XmlAccessType.FIELD)
public class Client implements IClient{
	/**
	 * L'identifiant du client.
	 */
	@Id
	@GeneratedValue
	@XmlElement(name = "client-id")
	private int id;
 
	/**
	 * Le nom du client.
	 */
	@Column(name = "nom", columnDefinition = "character(255)")
	@XmlElement(name = "nom")
	private String nom;
 
	/**
	 * La quantité d'argent que le client possède sur son compte de location.
	 */
	@Column(name = "compte")
	@XmlElement(name = "compte")
	private double compte;
	
	/**
	 * L'maile adresse du client
	 */
	@Column(name = "email")
	@XmlElement(name = "email")
	private String email;
	
	/**
	 * Le mot de passe du client, en hacher 
	 */
	@Column(name = "mdp")
	@XmlElement(name = "mdp")
	private String mdp;
	
/*	@OneToMany(mappedBy = "client", fetch=FetchType.EAGER)*/
	@OneToMany(mappedBy = "client")
	@XmlElement(name = "message")
	private List<Message> messages = new ArrayList<Message>();
	
	@OneToMany(mappedBy = "client")
	private List<Commande> commandArticles = new ArrayList<Commande>();
	
	public Client(){
		super();
	}
 
	public String getNom() {
		return nom;
	}
 
	public void setNom(String nom) {
		this.nom = nom;
	}
 
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public double getCompte() {
		return compte;
	}
 
	public void setCompte(double compte) {
		this.compte = compte;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}
	
	public Message newMessage() {
		Message m = new Message();
		m.setClient(this);
		messages.add(m);
		return m;
	}

	public Collection<Message> getMessages() {
		return new ArrayList<Message>(this.messages);
	}

	public void removeMessages(int nbMessages) {
		for (int i = 1; i <= nbMessages; i++) {
			Message m = messages.get(messages.size() - 1);
			m.setClient(null);
			messages.remove(messages.size() - 1);
		}
	}

	public List<Commande> getCommandArticles() {
		return commandArticles;
	}

	public void setCommandArticles(List<Commande> commandArticles) {
		this.commandArticles = commandArticles;
	}
	
	
}
