package it.unifi.ing.stlab.empedocle.services.appointments.jaxb;

import it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.Appointment;
import it.unifi.ing.stlab.empedocle.services.appointments.jaxb.patient.Patient;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;



/**
 * <p>Java class for handleAppointment complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="handleAppointment">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="application_sender" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="patient_details" type="{http://www.stlab.dsi.unifi.it/thislab/eai/patient}patient"/>
 *         &lt;element name="appointment_details" type="{http://www.stlab.dsi.unifi.it/thislab/eai/appointment}appointment"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "handleAppointment", propOrder = {
    "applicationSender",
    "timestamp",
    "patientDetails",
    "appointmentDetails"
})
public class HandleAppointment {

    @XmlElement(name = "application_sender", required = true)
    protected String applicationSender;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected Date timestamp;
    @XmlElement(name = "patient_details", required = true)
    protected Patient patientDetails;
    @XmlElement(name = "appointment_details", required = true)
    protected Appointment appointmentDetails;

    /**
     * Gets the value of the applicationSender property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationSender() {
        return applicationSender;
    }

    /**
     * Sets the value of the applicationSender property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationSender(String value) {
        this.applicationSender = value;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setTimestamp(Date value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the patientDetails property.
     * 
     * @return
     *     possible object is
     *     {@link Patient }
     *     
     */
    public Patient getPatientDetails() {
        return patientDetails;
    }

    /**
     * Sets the value of the patientDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link Patient }
     *     
     */
    public void setPatientDetails(Patient value) {
        this.patientDetails = value;
    }

    /**
     * Gets the value of the appointmentDetails property.
     * 
     * @return
     *     possible object is
     *     {@link Appointment }
     *     
     */
    public Appointment getAppointmentDetails() {
        return appointmentDetails;
    }

    /**
     * Sets the value of the appointmentDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link Appointment }
     *     
     */
    public void setAppointmentDetails(Appointment value) {
        this.appointmentDetails = value;
    }

}
