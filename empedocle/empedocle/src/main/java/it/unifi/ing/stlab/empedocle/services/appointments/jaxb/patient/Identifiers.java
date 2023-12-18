
package it.unifi.ing.stlab.empedocle.services.appointments.jaxb.patient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for identifiers complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="identifiers">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id_ace" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="id_people" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tax_code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ssn_code" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "identifiers", propOrder = {
    "idAce",
    "idPeople",
    "taxCode",
    "ssnCode"
})
public class Identifiers {

    @XmlElement(name = "id_ace", required = true)
    protected String idAce;
    @XmlElement(name = "id_people")
    protected String idPeople;
    @XmlElement(name = "tax_code", required = true)
    protected String taxCode;
    @XmlElement(name = "ssn_code")
    protected String ssnCode;

    /**
     * Gets the value of the idAce property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdAce() {
        return idAce;
    }

    /**
     * Sets the value of the idAce property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdAce(String value) {
        this.idAce = value;
    }

    /**
     * Gets the value of the idPeople property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdPeople() {
        return idPeople;
    }

    /**
     * Sets the value of the idPeople property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdPeople(String value) {
        this.idPeople = value;
    }

    /**
     * Gets the value of the taxCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxCode() {
        return taxCode;
    }

    /**
     * Sets the value of the taxCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxCode(String value) {
        this.taxCode = value;
    }

    /**
     * Gets the value of the ssnCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSsnCode() {
        return ssnCode;
    }

    /**
     * Sets the value of the ssnCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSsnCode(String value) {
        this.ssnCode = value;
    }

}
