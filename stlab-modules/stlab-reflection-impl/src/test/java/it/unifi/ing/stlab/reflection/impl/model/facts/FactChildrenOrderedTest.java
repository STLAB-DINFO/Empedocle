package it.unifi.ing.stlab.reflection.impl.model.facts;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactLinkImpl;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FactChildrenOrderedTest {

	protected FactLinkFactory linkFactory;
	protected CompositeFactImpl fact;
	protected FactLinkImpl factLink1, factLink2, factLink3;
	
	@Before
	public void setUp() {
		fact = FactFactory.createComposite();
		linkFactory = new FactLinkFactory();
		
		TypeLink typeLink1 = TypeLinkFactory.createLink();
		typeLink1.setPriority( 1l );
		TypeLink typeLink2 = TypeLinkFactory.createLink();
		typeLink2.setPriority( 0l );
		
		factLink1 = linkFactory.insertLink( fact, FactFactory.createTextual() );
		factLink1.setType( typeLink1 );
		factLink2 = linkFactory.insertLink( fact, FactFactory.createTextual() );
		factLink2.setType( typeLink1 );
		factLink3 =  linkFactory.insertLink( fact, FactFactory.createTextual() );
		factLink3.setType( typeLink2 );
	}
	
	@Test
	public void testChildrenOrdered1() {
		List<? extends FactLink> result = fact.listChildrenOrdered();
		
		assertEquals( 3, result.size() );
		assertEquals( factLink3, result.get( 0 ) );
	}
	
	@Test
	public void testChildrenOrdered2() {
		factLink1.setPriority( 3l );
		factLink2.setPriority( 4l );
		factLink3.setPriority( 5l );
		
		List<? extends FactLink> result = fact.listChildrenOrdered();
		assertEquals( 3, result.size() );
		assertEquals( factLink3, result.get( 0 ) );
		assertEquals( factLink1, result.get( 1 ) );
		assertEquals( factLink2, result.get( 2 ) );
	}
	
	@Test
	public void testChildrenOrdered3() {
		factLink1.setPriority( -1l );
		factLink2.setPriority( 0l );
		factLink3.setPriority( 5l );
		
		List<? extends FactLink> result = fact.listChildrenOrdered();
		assertEquals( 3, result.size() );
		assertEquals( factLink3, result.get( 0 ) );
		assertEquals( factLink1, result.get( 1 ) );
		assertEquals( factLink2, result.get( 2 ) );
	}
	
}
