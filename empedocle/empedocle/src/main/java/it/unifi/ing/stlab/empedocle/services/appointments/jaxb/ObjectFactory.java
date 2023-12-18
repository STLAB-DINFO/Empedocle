
package it.unifi.ing.stlab.empedocle.services.appointments.jaxb;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.unifi.dsi.stlab.thislab.eai.appointment_message package. 
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

    private final static QName _HandleAppointment_QNAME = new QName("http://www.stlab.dsi.unifi.it/thislab/eai/appointment-message", "handleAppointment");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.unifi.dsi.stlab.thislab.eai.appointment_message
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HandleAppointment }
     * 
     */
    public HandleAppointment createHandleAppointment() {
        return new HandleAppointment();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HandleAppointment }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.stlab.dsi.unifi.it/thislab/eai/appointment-message", name = "handleAppointment")
    public JAXBElement<HandleAppointment> createHandleAppointment(HandleAppointment value) {
        return new JAXBElement<HandleAppointment>(_HandleAppointment_QNAME, HandleAppointment.class, null, value);
    }

}
