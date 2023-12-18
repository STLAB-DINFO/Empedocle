
package it.unifi.ing.stlab.empedocle.services.appointments.jaxb.patient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.unifi.dsi.stlab.thislab.eai.patient package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Patient_QNAME = new QName("http://www.stlab.dsi.unifi.it/thislab/eai/patient", "patient");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.unifi.dsi.stlab.thislab.eai.patient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Phones }
     * 
     */
    public Phones createPhones() {
        return new Phones();
    }

    /**
     * Create an instance of {@link Identifiers }
     * 
     */
    public Identifiers createIdentifiers() {
        return new Identifiers();
    }

    /**
     * Create an instance of {@link Patient }
     * 
     */
    public Patient createPatient() {
        return new Patient();
    }

    /**
     * Create an instance of {@link Place }
     * 
     */
    public Place createPlace() {
        return new Place();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Patient }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.stlab.dsi.unifi.it/thislab/eai/patient", name = "patient")
    public JAXBElement<Patient> createPatient(Patient value) {
        return new JAXBElement<Patient>(_Patient_QNAME, Patient.class, null, value);
    }

}
