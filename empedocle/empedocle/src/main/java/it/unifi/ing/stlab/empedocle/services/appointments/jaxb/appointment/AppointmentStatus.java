
package it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for appointment_status.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="appointment_status">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="BOOKED"/>
 *     &lt;enumeration value="ACCEPTED"/>
 *     &lt;enumeration value="CANCELED"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "appointment_status")
@XmlEnum
public enum AppointmentStatus {

    BOOKED("BOOKED"),
    ACCEPTED("ACCEPTED"),
    CANCELED("CANCELED");
    private final String value;

    AppointmentStatus(String v) {
        value = v;
    }
    
    public String value() {
        return value;
    }

    public static AppointmentStatus fromValue(String v) {
        for (AppointmentStatus c: AppointmentStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
