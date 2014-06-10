package vin.dal.db.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import vin.dal.db.intf.IClient;
import vin.dal.db.intf.IMessage;

@Entity
@Table(name = "message")
public class Message implements IMessage, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", columnDefinition = "integer")
	private Long id;
	
	@Column(name = "titreMessage", columnDefinition = "character(255)")
	@XmlElement(name = "titreMessage")
	private String titreMessage;
	
	@ManyToOne
	private Client client;
	
	@Column(name = "contenuMessage", columnDefinition = "character(2000)")
	private String contenuMessage;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_envoyeMessage")
	private Date dateEnvoyeMessage;
	
	@Override
	@XmlAttribute
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	@Override
	@XmlTransient
	public IClient getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public String getTitreMessage() {
		return titreMessage;
	}

	public void setTitreMessage(String titreMessage) {
		this.titreMessage = titreMessage;
	}

	public String getContenuMessage() {
		return contenuMessage;
	}

	public void setContenuMessage(String contenuMessage) {
		this.contenuMessage = contenuMessage;
	}

	public Date getDateEnvoyeMessage() {
		return dateEnvoyeMessage;
	}

	public void setDateEnvoyeMessage(Date dateEnvoyeMessage) {
		this.dateEnvoyeMessage = dateEnvoyeMessage;
	}

}
