
package fr.univ_lyon1.m2ti.services.paiement;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
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
 *         &lt;element name="montant" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="cmdid" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "montant",
    "cmdid"
})
@XmlRootElement(name = "paiement")
public class Paiement {

    protected double montant;
    @XmlElement(required = true)
    protected String cmdid;

    /**
     * Gets the value of the montant property.
     * 
     */
    public double getMontant() {
        return montant;
    }

    /**
     * Sets the value of the montant property.
     * 
     */
    public void setMontant(double value) {
        this.montant = value;
    }

    /**
     * Gets the value of the cmdid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCmdid() {
        return cmdid;
    }

    /**
     * Sets the value of the cmdid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCmdid(String value) {
        this.cmdid = value;
    }

}
