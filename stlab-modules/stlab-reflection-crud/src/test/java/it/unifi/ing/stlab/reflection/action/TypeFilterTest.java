package it.unifi.ing.stlab.reflection.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.reflection.actions.TypeFilter;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;

public class TypeFilterTest {

	protected EntityManager entityManager;
	protected Query query;
	protected TypeFilter typeFilter;
	
	@Before
	public void setUp() {
		query = mock( Query.class );
		when( query.setParameter( anyString(), anyObject() )).thenReturn( query );
		
		entityManager = mock( EntityManager.class );
		when( entityManager.createQuery( anyString() )).thenReturn( query );
		
		typeFilter = new TypeFilter();
	}

	@Test
	public void testIsSelected() {
		assertTrue( typeFilter.isSelected( "Nome" ));
	}

	@Test
	public void testToggle() {
		typeFilter.toggle( "Nome" );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testToggleFail() {
		typeFilter.toggle( "ijk" );
	}

	
	@Test
	public void testIsAscending() {
		assertTrue( typeFilter.isAscending( "Nome" ));
		
		typeFilter.toggle( "Nome" );
		assertFalse( typeFilter.isAscending( "Nome" ));

		typeFilter.toggle( "Nome" );
		assertTrue( typeFilter.isAscending( "Nome" ));
	}
	
	@Test
	public void testListQueryNoFilter() {
		assertNotNull( typeFilter.getListQuery( entityManager ));
	
		verify( entityManager ).createQuery( "select t " +
											 " from it.unifi.ing.stlab.reflection.model.types.Type t " +
											 " where t.anonymous = false order by t.name asc" );
		verify( query, never() ).setParameter( anyString(), anyObject() );
	}

	// TODO test selectList
	// TODO test isSelectedList
}
