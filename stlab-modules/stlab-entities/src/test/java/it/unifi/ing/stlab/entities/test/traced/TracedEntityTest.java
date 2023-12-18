package it.unifi.ing.stlab.entities.test.traced;

import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeCreateAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeDeleteAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeModifyAction;
import it.unifi.ing.stlab.entities.test.traced.fakes.FakeTracedEntity;

import org.junit.Before;
import org.junit.Test;

public class TracedEntityTest extends ActionTest {

	protected FakeTracedEntity tracedEntity;
	
	@Before
	public void setUp() {
		super.setUp();
		tracedEntity = new FakeTracedEntity();
		tracedEntity.init();
	}
	
	@Test
	public void testDelete1() {
		tracedEntity.delete();
		verify( garbageCollector ).garbage( tracedEntity );
	}

	@Test
	public void testDelete2() {
		FakeCreateAction createAction = createAction();
		FakeDeleteAction deleteAction = deleteAction();
		
		createAction.assignTarget( tracedEntity );
		deleteAction.assignSource( tracedEntity );
		
		tracedEntity.delete();
		verify( garbageCollector ).garbage( tracedEntity );
		verify( garbageCollector ).garbage( createAction );
		verify( garbageCollector ).garbage( deleteAction );
	}
	
	@Test( expected = UnsupportedOperationException.class )
	public void testDelete3() {
		FakeModifyAction modifyAction = modifyAction();
		modifyAction.assignSource( tracedEntity );
	
		tracedEntity.delete();
	}
	
	@Test( expected = UnsupportedOperationException.class )
	public void testBeforeReadOnly() {
		tracedEntity.listBefore().add( null );
	}
	
	@Test( expected = UnsupportedOperationException.class )
	public void testAfterReadOnly() {
		tracedEntity.listAfter().add( null );
	}
}
