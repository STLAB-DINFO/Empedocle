package it.unifi.ing.stlab.empedocle.services.appointments.jaxb.patient;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for patient complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="patient">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identifiers" type="{http://www.stlab.dsi.unifi.it/thislab/eai/patient}identifiers"/>
 *         &lt;element name="surname" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sex" type="{http://www.stlab.dsi.unifi.it/thislab/eai/patient}sex"/>
 *         &lt;element name="birth_date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="birth_place" type="{http://www.stlab.dsi.unifi.it/thislab/eai/patient}place"/>
 *         &lt;element name="asl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="region" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nationality" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="residence" type="{http://www.stlab.dsi.unifi.it/thislab/eai/patient}place" minOccurs="0"/>
 *         &lt;element name="domicile" type="{http://www.stlab.dsi.unifi.it/thislab/eai/patient}place" minOccurs="0"/>
 *         &lt;element name="phones" type="{http://www.stlab.dsi.unifi.it/thislab/eai/patient}phones" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "patient", propOrder = {
    "identifiers",
    "surname",
    "name",
    "sex",
    "birthDate",
    "birthPlace",
    "asl",
    "region",
    "nationality",
    "residence",
    "domicile",
    "phones"
})
public class Patient {

    @XmlElement(required = true)
    protected Identifiers identifiers;
    @XmlElement(required = true)
    protected String surname;
    @XmlElement(required = true)
    protected String name;
    @XmlElement(required = true)
    protected Sex sex;
    @XmlElement(name = "birth_date", required = true)
    @XmlSchemaType(name = "date")
    protected Date birthDate;
    @XmlElement(name = "birth_place", required = true)
    protected Place birthPlace;
    protected String asl;
    protected String region;
    protected String nationality;
    protected Place residence;
    protected Place domicile;
    protected Phones phones;

    /**
     * Gets the value of the identifiers property.
     * 
     * @return
     *     possible object is
     *     {@link Identifiers }
     *     
     */
    public Identifiers getIdentifiers() {
        return identifiers;
    }

    /**
     * Sets the value of the identifiers property.
     * 
     * @param value
     *     allowed object is
     *     {@link Identifiers }
     *     
     */
    public void setIdentifiers(Identifiers value) {
        this.identifiers = value;
    }

    /**
     * Gets the value of the surname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the value of the surname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSurname(String value) {
        this.surname = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the sex property.
     * 
     * @return
     *     possible object is
     *     {@link Sex }
     *     
     */
    public Sex getSex() {
        return sex;
    }

    /**
     * Sets the value of the sex property.
     * 
     * @param value
     *     allowed object is
     *     {@link Sex }
     *     
     */
    public void setSex(Sex value) {
        this.sex = value;
    }

    /**
     * Gets the value of the birthDate property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * Sets the value of the birthDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setBirthDate(Date value) {
        this.birthDate = value;
    }

    /**
     * Gets the value of the birthPlace property.
     * 
     * @return
     *     possible object is
     *     {@link Place }
     *     
     */
    public Place getBirthPlace() {
        return birthPlace;
    }

    /**
     * Sets the value of the birthPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link Place }
     *     
     */
    public void setBirthPlace(Place value) {
        this.birthPlace = value;
    }

    /**
     * Gets the value of the asl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAsl() {
        return asl;
    }

    /**
     * Sets the value of the asl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAsl(String value) {
        this.asl = value;
    }

    /**
     * Gets the value of the region property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegion() {
        return region;
    }

    /**
     * Sets the value of the region property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegion(String value) {
        this.region = value;
    }

    /**
     * Gets the value of the nationality property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNationality() {
        return nationality;
    }

    /**
     * Sets the value of the nationality property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNationality(String value) {
        this.nationality = value;
    }

    /**
     * Gets the value of the residence property.
     * 
     * @return
     *     possible object is
     *     {@link Place }
     *     
     */
    public Place getResidence() {
        return residence;
    }

    /**
     * Sets the value of the residence property.
     * 
     * @param value
     *     allowed object is
     *     {@link Place }
     *     
     */
    public void setResidence(Place value) {
        this.residence = value;
    }

    /**
     * Gets the value of the domicile property.
     * 
     * @return
     *     possible object is
     *     {@link Place }
     *     
     */
    public Place getDomicile() {
        return domicile;
    }

    /**
     * Sets the value of the domicile property.
     * 
     * @param value
     *     allowed object is
     *     {@link Place }
     *     
     */
    public void setDomicile(Place value) {
        this.domicile = value;
    }

    /**
     * Gets the value of the phones property.
     * 
     * @return
     *     possible object is
     *     {@link Phones }
     *     
     */
    public Phones getPhones() {
        return phones;
    }

    /**
     * Sets the value of the phones property.
     * 
     * @param value
     *     allowed object is
     *     {@link Phones }
     *     
     */
    public void setPhones(Phones value) {
        this.phones = value;
    }

}
