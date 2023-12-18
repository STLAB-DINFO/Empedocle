package it.unifi.ing.stlab.reflection.model.types;

import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import org.junit.Before;
import org.junit.Test;

public class TypeLinkTest {

	protected TypeLink link;
	
	@Before
	public void setUp() {
		link = TypeLinkFactory.createLink();
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testTextual() {
		link.assignSource( TypeFactory.createTextualType() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testQuantitative() {
		link.assignSource( TypeFactory.createQuantitativeType() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testEnumerated() {
		link.assignSource( TypeFactory.createEnumeratedType() );
	}

	@Test( expected = IllegalArgumentException.class )
	public void testQueried() {
		link.assignSource( TypeFactory.createQueriedType() );
	}
	
	@Test
	public void testComposite() {
		link.assignSource( TypeFactory.createCompositeType() );
		assertNotNull( link.getSource() );
	}
	
}
