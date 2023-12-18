
package it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.unifi.dsi.stlab.thislab.eai.appointment package. 
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

    private final static QName _Appointment_QNAME = new QName("http://www.stlab.dsi.unifi.it/thislab/eai/appointment", "appointment");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.unifi.dsi.stlab.thislab.eai.appointment
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Appointment }
     * 
     */
    public Appointment createAppointment() {
        return new Appointment();
    }

    /**
     * Create an instance of {@link Service }
     * 
     */
    public Service createService() {
        return new Service();
    }

    /**
     * Create an instance of {@link Services }
     * 
     */
    public Services createServices() {
        return new Services();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Appointment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.stlab.dsi.unifi.it/thislab/eai/appointment", name = "appointment")
    public JAXBElement<Appointment> createAppointment(Appointment value) {
        return new JAXBElement<Appointment>(_Appointment_QNAME, Appointment.class, null, value);
    }

}
