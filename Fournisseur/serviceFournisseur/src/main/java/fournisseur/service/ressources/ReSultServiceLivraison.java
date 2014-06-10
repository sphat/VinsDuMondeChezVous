package fournisseur.service.ressources;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="resultLivraison")
@XmlAccessorType(XmlAccessType.FIELD)
public class ReSultServiceLivraison {
	@XmlElement(name="result")
	private
	String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
