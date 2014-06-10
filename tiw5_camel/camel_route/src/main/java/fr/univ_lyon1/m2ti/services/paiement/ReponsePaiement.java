
package fr.univ_lyon1.m2ti.services.paiement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ok" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="raison" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "ok",
    "raison"
})
@XmlRootElement(name = "reponse-paiement")
public class ReponsePaiement {

    protected boolean ok;
    protected String raison;

    /**
     * Gets the value of the ok property.
     * 
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * Sets the value of the ok property.
     * 
     */
    public void setOk(boolean value) {
        this.ok = value;
    }

    /**
     * Gets the value of the raison property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRaison() {
        return raison;
    }

    /**
     * Sets the value of the raison property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRaison(String value) {
        this.raison = value;
    }

}
