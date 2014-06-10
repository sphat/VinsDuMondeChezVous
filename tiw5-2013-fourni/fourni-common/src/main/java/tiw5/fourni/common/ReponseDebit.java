package tiw5.fourni.common;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "reponse-debit", namespace="http://www.univ-lyon1.fr/M2TI/services/mabanque")
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class ReponseDebit {

	@XmlElement(name = "accorde", required = true)
	public boolean debitAccorde;

	@XmlElement(name = "raison")
	public String raison;
}
