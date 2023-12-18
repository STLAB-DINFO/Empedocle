package it.unifi.ing.stlab.view.actions;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.view.actions.ViewerFilter;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

public class ViewerFilterTest {

	protected EntityManager entityManager;
	protected Query query;
	protected ViewerFilter viewFilter;
	
	@Before
	public void setUp() {
		query = mock( Query.class );
		when( query.setParameter( anyString(), anyObject() )).thenReturn( query );
		
		entityManager = mock( EntityManager.class );
		when( entityManager.createQuery( anyString() )).thenReturn( query );
		
		viewFilter = new ViewerFilter();
	}
	
	@Test
	public void testGetCountQueryNoFilter(){
		assertNotNull( viewFilter.getCountQuery( entityManager ));
		
		verify( entityManager ).createQuery( "select count( distinct v ) from Viewer v where v.anonymous = false " );
		verify( query, never() ).setParameter( anyString(), anyObject() );
		
	}
	
	@Test
	public void testListQueryNoFilter() {
		assertNotNull( viewFilter.getListQuery( entityManager ));
	
		verify( entityManager ).createQuery( "select distinct v from Viewer v where v.anonymous = false  order by v.name asc" );
		verify( query, never() ).setParameter( anyString(), anyObject() );
	}
	
}
