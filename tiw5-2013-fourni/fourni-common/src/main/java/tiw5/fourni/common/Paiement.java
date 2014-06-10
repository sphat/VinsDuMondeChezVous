package tiw5.fourni.common;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "paiement", namespace="http://www.univ-lyon1.fr/M2TI/services/paiement")
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class Paiement {
	@XmlElement(name = "montant", required = true)
	public double montant;
	@XmlElement(name = "cmdid", required = true)
	public String cmdid;
}
