package tiw5.fourni.common;

import javax.xml.bind.annotation.XmlAttribute;

public class Compte {

	private long numeroCarte;
	private int cryptogramme;
	private String client;
	private double valeur = 0.0;

	@XmlAttribute(name = "carte", required = true)
	public long getNumeroCarte() {
		return numeroCarte;
	}

	public void setNumeroCarte(long numeroCarte) {
		this.numeroCarte = numeroCarte;
	}

	@XmlAttribute(name = "crypt", required = true)
	public int getCryptogramme() {
		return cryptogramme;
	}

	public void setCryptogramme(int cryptogramme) {
		this.cryptogramme = cryptogramme;
	}

	@XmlAttribute(name = "client", required = true)
	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	@XmlAttribute(name = "valeur")
	public double getValeur() {
		return valeur;
	}

	public void setValeur(double valeur) {
		this.valeur = valeur;
	}

	public void virement(double combien) {
		this.valeur += combien;
	}

}
