package tiw5.fourni.common;

import javax.xml.bind.annotation.XmlAccessOrder;
import javax.xml.bind.annotation.XmlAccessorOrder;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="reponse-paiement", namespace="http://www.univ-lyon1.fr/M2TI/services/paiement")
@XmlAccessorOrder(XmlAccessOrder.ALPHABETICAL)
public class ReponsePaiement {

	@XmlElement(name = "ok", required = true)
	public boolean reussi;

	@XmlElement(name = "raison")
	public String raison;

}
