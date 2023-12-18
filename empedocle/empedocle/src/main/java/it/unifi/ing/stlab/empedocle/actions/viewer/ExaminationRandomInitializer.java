package it.unifi.ing.stlab.empedocle.actions.viewer;

import it.unifi.ing.stlab.empedocle.factory.AgendaFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ServiceFactory;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.Appointment;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.empedocle.model.health.Service;
import it.unifi.ing.stlab.patients.factory.PatientFactory;
import it.unifi.ing.stlab.patients.model.Address;
import it.unifi.ing.stlab.patients.model.Patient;
import it.unifi.ing.stlab.patients.model.Sex;
import it.unifi.ing.stlab.reflection.model.facts.FactContext;
import it.unifi.ing.stlab.users.factory.QualificationFactory;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.Qualification;
import it.unifi.ing.stlab.users.model.User;

import java.util.Date;
import java.util.UUID;

public class ExaminationRandomInitializer {

	public FactContext initNewExamination(){
		Examination examination = ExaminationFactory.createExamination();
		Agenda agenda = initAgenda();
		Service service = initService( agenda );
		Patient patient = initPatient();
		Appointment appointment = initAppointment( patient, service );
		examination.setAppointment( appointment );
		examination.setLastUpdate( new Date() );
		examination.setAuthor( initUser() );
		
		return examination;
	}
	
	private Appointment initAppointment(Patient patient, Service service){
		Appointment appointment = new Appointment(UUID.randomUUID().toString());
		appointment.setDate(new Date());
		appointment.setAcceptanceCode("AXX120XX83149");
		appointment.setBookingCode("0901XXX882XXX32");
		appointment.setNumber("123456");
		appointment.setPatient(patient);
		appointment.addService(service);
		appointment.setAgenda(service.getAgenda());
		
		return appointment;
	}
	
	private Service initService(Agenda agenda) {
		Service service = ServiceFactory.createService();
		service.setCode("SERVICE CODE XYZ");
		service.setDescription("SERVICE DESCRIPTION XYZ");
		service.setAgenda(agenda);
		
		return service;
	}
	
	private Patient initPatient() {
		Patient patient = PatientFactory.createPatient();
		
		patient.setAsl("AZ. USL 10 - FIRENZE");
		patient.setTaxCode("RSSGVN14F56P543D");
		patient.setSsnCode("XYZ");
		patient.setSurname("ROSSI");
		patient.setBirthDate(new Date());
		patient.setBirthPlace("FIRENZE");
		patient.setNationality("ITALIA");
		patient.setName("GIOVANNI");
		patient.setRegion("TOSCANA");
		patient.setSex(Sex.M);
		patient.setHomePhone("055 667788");
		patient.setWorkPhone("329 345678901");
		
		Address domicile = new Address();
		domicile.setPlace("VIA DI VILLA MAGNA 76 FIRENZE");
		patient.setDomicile(domicile);
		
		Address residence = new Address();
		residence.setPlace("PARCO DELLA VITTORIA XX FIRENZE");
		patient.setResidence(residence);
		
		return patient;
	}
	
	private Agenda initAgenda(){
		Agenda agenda = AgendaFactory.createAgenda();
		
		agenda.setCode("50X3");
		agenda.setDescription("DESCRIZIONE AGENDA - PROVA PER TEST");
		
		return agenda;
	}
	
	private User initUser(){
		User user = UserFactory.createUser();
		user.setName("Nome");
		user.setSurname("Cognome");
		
		Qualification qualification1 = QualificationFactory.createQualification();
		qualification1.setName("medico");
		user.addQualification(qualification1);
		
		return user;
	}

}
