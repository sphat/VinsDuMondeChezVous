package tiw5.fourni.common;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "debit", namespace="http://www.univ-lyon1.fr/M2TI/services/mabanque")
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class Debit {

	@XmlElement(name = "carte", required = true)
	public long numeroCarte;

	@XmlElement(name = "crypt", required = true)
	public int crytogramme;

	@XmlElement(name = "montant", required = true)
	public double montant;

}
