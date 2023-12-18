package it.unifi.ing.stlab.reflection.dao.types;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.reflection.dao.types.fake.QualitativeFactFake;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

public class PhenomenonDaoTest {

	protected PhenomenonDao phenomenonDao;
	protected Date dateA;
	protected Date dateB;
	protected Date dateC;
	protected Date dateD;
	
	@Before
	public void setUp() throws Exception{
		phenomenonDao = new PhenomenonDaoBean();
		Calendar cal = Calendar.getInstance();
		cal.set(2012, 04, 01);
		dateA = cal.getTime();
		
		cal.set(2013, 01, 01);
		dateB = cal.getTime();
		
		cal.set(2013, 04, 01);
		dateC = cal.getTime();
		
		cal.set(2013, 03, 01);
		dateD = cal.getTime();
	}
	
	@Test
	public void testGetFromQualitativeEnumerated(){
		EnumeratedType et = TypeFactory.createEnumeratedType();
		
		Phenomenon f1 = PhenomenonFactory.createPhenomenon();
		f1.setName("fen1");
		f1.setTimeRange(new TimeRange(new Time(dateA), new Time(dateB)));
		et.addPhenomenon(f1);
		
		Phenomenon f2 = PhenomenonFactory.createPhenomenon();
		f2.setName("fen2");
		f2.setTimeRange(new TimeRange(new Time(dateB), new Time(dateC)));
		et.addPhenomenon(f2);
		
		Phenomenon f3 = PhenomenonFactory.createPhenomenon();
		f3.setName("fen3");
		f3.setTimeRange(new TimeRange(new Time(dateB), new Time(dateD)));
		et.addPhenomenon(f3);
		
		QualitativeFact qt = new QualitativeFactFake();
		qt.assignType(et);
		
		List<Phenomenon> result = phenomenonDao.findByFact(qt, dateD);
		assertEquals(2, result.size());
		assertFalse(result.contains(f1));
		assertTrue(result.contains(f2));
		assertTrue(result.contains(f3));
	}
	
	@Test
	public void testGetFromQualitativeQueried(){
		EntityManager entityManager = mock( EntityManager.class );
		Query query = mock( Query.class );
		FieldUtils.assignField(phenomenonDao, "entityManager", entityManager);
		
		when( entityManager.createQuery( anyString() )).thenReturn( query );
		
		QueriedType qt = TypeFactory.createQueriedType();
		qt.setQueryDef("select a from ...");
		
		List<Phenomenon> resultSet = new LinkedList<Phenomenon>();
		Phenomenon f1 = PhenomenonFactory.createPhenomenon();
		f1.setName("fen1");
		f1.setTimeRange(new TimeRange(new Time(dateA), new Time(dateB)));
		resultSet.add(f1);
		
		Phenomenon f2 = PhenomenonFactory.createPhenomenon();
		f2.setName("fen2");
		f2.setTimeRange(new TimeRange(new Time(dateB), new Time(dateC)));
		resultSet.add(f2);
		
		Phenomenon f3 = PhenomenonFactory.createPhenomenon();
		f3.setName("fen3");
		f3.setTimeRange(new TimeRange(new Time(dateB), new Time(dateD)));
		resultSet.add(f3);
		
		when(query.getResultList()).thenReturn(resultSet);
		
		QualitativeFact qf = new QualitativeFactFake();
		qf.assignType(qt);
		
		List<Phenomenon> result = phenomenonDao.findByFact(qf, dateD);
		assertEquals(2, result.size());
		assertFalse(result.contains(f1));
		assertTrue(result.contains(f2));
		assertTrue(result.contains(f3));
	}
	
	@Test
	public void testFindByUuidFail1(){
		assertNull(phenomenonDao.findByUuid(null));
		
	}
	
	@Test
	public void testFindByUuidFail2(){
		assertNull(phenomenonDao.findByUuid("  "));
		
	}
	
}
