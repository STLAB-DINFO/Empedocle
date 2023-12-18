package it.unifi.ing.stlab.empedocle.services.appointments;

import it.unifi.ing.stlab.empedocle.services.appointments.jaxb.appointment.Appointment;
import it.unifi.ing.stlab.empedocle.services.appointments.jaxb.patient.Patient;

import java.util.Date;

import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;

@WebService(name = "appointmentService", 
	targetNamespace = "http://www.stlab.dsi.unifi.it/thislab/eai/appointment-service")
public interface AppointmentService {


    /**
     * 
     * @param timestamp
     * @param applicationSender
     * @param appointmentDetails
     * @param patientDetails
     */
    @WebMethod(action = "urn:appointmentService.handleAppointment")
    @Oneway
    @RequestWrapper(localName = "handleAppointment", targetNamespace = "http://www.stlab.dsi.unifi.it/thislab/eai/appointment-message", className = "it.unifi.ing.stlab.empedocle.services.appointments.jaxb.HandleAppointment")
    public void handleAppointment(
        @WebParam(name = "application_sender", targetNamespace = "http://www.stlab.dsi.unifi.it/thislab/eai/appointment-message")
        String applicationSender,
        @WebParam(name = "timestamp", targetNamespace = "http://www.stlab.dsi.unifi.it/thislab/eai/appointment-message")
        Date timestamp,
        @WebParam(name = "patient_details", targetNamespace = "http://www.stlab.dsi.unifi.it/thislab/eai/appointment-message")
        Patient patientDetails,
        @WebParam(name = "appointment_details", targetNamespace = "http://www.stlab.dsi.unifi.it/thislab/eai/appointment-message")
        Appointment appointmentDetails);

}
