package fournisseur.service.ressources;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="parametre")
@XmlAccessorType(XmlAccessType.FIELD)
public class ParametreAjouterCommande {
	
	@XmlElementWrapper(name = "listArcticle") @XmlElement(name = "article")
	private Article[] article;
	
	@XmlElement(name="nomDuMasin")
	private String nomDuMasin;

	public String getNomDuMasin() {
		return nomDuMasin;
	}

	public void setNomDuMasin(String nomDuMasin) {
		this.nomDuMasin = nomDuMasin;
	}

	public Article[] getArticle() {
		return article;
	}

	public void setArticle(Article[] article) {
		this.article = article;
	}

}
