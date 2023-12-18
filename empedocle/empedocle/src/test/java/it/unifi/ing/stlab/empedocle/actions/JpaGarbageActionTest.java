package it.unifi.ing.stlab.empedocle.actions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.entities.implementation.GarbageAction;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;

public class JpaGarbageActionTest {

	protected GarbageAction action;
	protected EntityManager entityManager;
	
	@Before
	public void setUp() {
		entityManager = mock( EntityManager.class );
		action = new JpaGarbageAction( entityManager );
	}
	
	@Test
	public void testTrue() {
		when( entityManager.contains( anyObject() )).thenReturn( true );
		
		assertTrue( action.execute( "ABC" ));
		verify( entityManager ).remove( "ABC" );
	}
	
	@Test
	public void testFalse() {
		when( entityManager.contains( anyObject() )).thenReturn( false );
		
		assertFalse( action.execute( "ABC" ));
		verify( entityManager, never() ).remove( "ABC" );
	}
	
}

