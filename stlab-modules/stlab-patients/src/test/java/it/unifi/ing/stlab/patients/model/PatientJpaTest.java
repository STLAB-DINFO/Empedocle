package it.unifi.ing.stlab.patients.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.patients.factory.PatientFactory;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import java.sql.Date;

import org.junit.Test;

public class PatientJpaTest extends PersistenceTest {

	protected String uuid;
	protected PatientIdentifier identifier;
	protected TimeRange period;
	
	@Override
	protected void insertData() throws Exception {
		period = new TimeRange( 
			new Time( DateHelper.createDate( "2013-03-01") ), 
			new Time( DateHelper.createDate( "2013-03-31")));

		Patient patient = PatientFactory.createPatient();
		
		identifier = PatientFactory.createPatientIdentifier();
		identifier.setCode( "id" );
		patient.setIdentifier( identifier );
		patient.setName( "name" );
		patient.setSurname( "surname" );
		patient.setSex( Sex.M );
		patient.setBirthDate( new Date( DateHelper.createDate( "2013-03-01").getTime() ));
		patient.setBirthPlace( "birthPlace" );
		patient.setTaxCode( "taxCode" );
		patient.setSsnCode( "ssnCode" );

		Address residence = new Address();
		residence.setPlace( "residence" );
		patient.setResidence( residence );

		Address domicile = new Address();
		domicile.setPlace( "domicile" );
		patient.setDomicile( domicile );
		
		patient.setRegion( "region" );
		patient.setHomePhone( "homePhone" );
		patient.setWorkPhone( "workPhone" );
		patient.setNationality( "nationality" );
		patient.setAsl( "asl" );
		
		entityManager.persist( patient );

		uuid = patient.getUuid();
	}
	
	@Test
	public void testPatient() {
		Patient patient = (Patient)
			entityManager
				.createQuery( 
					"select p " +
					" from Patient p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( patient );
		
		assertEquals( identifier, patient.getIdentifier());
		assertEquals( "name", patient.getName());
		assertEquals( "surname", patient.getSurname());
		assertEquals( Sex.M, patient.getSex());
		assertEquals( new Date( DateHelper.createDate( "2013-03-01").getTime() ), patient.getBirthDate());
		assertEquals( "birthPlace", patient.getBirthPlace());
		assertEquals( "taxCode", patient.getTaxCode());
		assertEquals( "ssnCode", patient.getSsnCode());
		assertEquals( "residence", patient.getResidence().getPlace());
		assertEquals( "domicile", patient.getDomicile().getPlace());
		assertEquals( "region", patient.getRegion());
		assertEquals( "homePhone", patient.getHomePhone());
		assertEquals( "workPhone", patient.getWorkPhone());
		assertEquals( "nationality", patient.getNationality());
		assertEquals( "asl", patient.getAsl());
	}
	
}
