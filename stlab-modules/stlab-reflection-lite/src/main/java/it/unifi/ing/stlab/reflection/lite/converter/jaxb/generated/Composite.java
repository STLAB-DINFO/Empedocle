//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.24 at 03:12:20 PM CET 
//


package it.unifi.ing.stlab.reflection.lite.converter.jaxb.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
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
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element ref="{http://www.stlab.dsi.unifi.it/reflection/facts}textual"/>
 *         &lt;element ref="{http://www.stlab.dsi.unifi.it/reflection/facts}temporal"/>
 *         &lt;element ref="{http://www.stlab.dsi.unifi.it/reflection/facts}qualitative"/>
 *         &lt;element ref="{http://www.stlab.dsi.unifi.it/reflection/facts}quantitative"/>
 *         &lt;element ref="{http://www.stlab.dsi.unifi.it/reflection/facts}composite"/>
 *       &lt;/choice>
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "children"
})
@XmlRootElement(name = "composite")
public class Composite {

    @XmlElements({
        @XmlElement(name = "textual", type = Textual.class),
        @XmlElement(name = "temporal", type = Temporal.class),
        @XmlElement(name = "qualitative", type = Qualitative.class),
        @XmlElement(name = "quantitative", type = Quantitative.class),
        @XmlElement(name = "composite", type = Composite.class)
    })
    protected List<Object> children;
    @XmlAttribute(name = "type", required = true)
    protected String type;

    /**
     * Gets the value of the children property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the children property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChildren().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Textual }
     * {@link Temporal }
     * {@link Qualitative }
     * {@link Quantitative }
     * {@link Composite }
     * 
     * 
     */
    public List<Object> getChildren() {
        if (children == null) {
            children = new ArrayList<Object>();
        }
        return this.children;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}