package it.unifi.ing.stlab.empedocle.actions.health;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

import it.unifi.ing.stlab.empedocle.actions.health.examination.types.ExaminationTypeFilter;

public class ExaminationTypeFilterTest {

	
	protected EntityManager entityManager;
	protected Query query;
	protected ExaminationTypeFilter examTypeFilter;
	
	@Before
	public void setUp(){
		query = mock( Query.class );
		when( query.setParameter( anyString(), anyObject() )).thenReturn( query );
		entityManager = mock( EntityManager.class );
		when( entityManager.createQuery( anyString() )).thenReturn( query );
		
		examTypeFilter = new ExaminationTypeFilter();
	}
	
	@Test
	public void testGetCountQueryNoFilter(){
		assertNotNull( examTypeFilter.getCountQuery( entityManager ));
		
		verify( entityManager ).createQuery( "select count( distinct et ) from ExaminationType et" );
		verify( query, never() ).setParameter( anyString(), anyObject() );
	}
	
	@Test
	public void testListQueryNoFilter() {
		assertNotNull( examTypeFilter.getListQuery( entityManager ));
		
		verify( entityManager ).createQuery( "select distinct et from ExaminationType et order by et.name asc" );
		verify( query, never() ).setParameter( anyString(), anyObject() );
	}
	
}
