package it.unifi.ing.stlab.reflection.impl.visitor.fact;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.model.facts.CompositeFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.TextualFactImpl;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

public class FactFinderTest {

	@Test
	public void testFound() {

		CompositeType ct = TypeFactory.createCompositeType();
		TextualType tt = TypeFactory.createTextualType();
		
		CompositeFact cf = FactFactory.createComposite();
		cf.assignType( ct );
		TextualFact tf = FactFactory.createTextual();
		tf.assignType( tt );
		
		FactLinkFactory flf = new FactLinkFactory();
		FactLink fl = flf.insertLink((CompositeFactImpl)cf, (TextualFactImpl)tf);
		
		TypeLink tl = TypeLinkFactory.createLink();
		tl.setName( "link1" );
		fl.setType(tl);
		
		FactFinder finder = new FactFinder();
		List<Fact> result = finder.find( cf, "link1" );
		
		assertNotNull( result );
		assertEquals( 1, result.size() );
		assertEquals( tf, result.get( 0 ) );
	}
	
	@Test
	public void testDepth() {
		CompositeType root = TypeFactory.createCompositeType();
		CompositeType ct = TypeFactory.createCompositeType();
		TextualType tt = TypeFactory.createTextualType();
		
		CompositeFact rootf = FactFactory.createComposite();
		rootf.assignType( root );
		CompositeFact cf = FactFactory.createComposite();
		cf.assignType( ct );
		TextualFact tf = FactFactory.createTextual();
		tf.assignType( tt );
		
		FactLinkFactory flf = new FactLinkFactory();
		FactLink fl1 = flf.insertLink((CompositeFactImpl)rootf, (CompositeFactImpl)cf);
		FactLink fl2 = flf.insertLink((CompositeFactImpl)cf, (TextualFactImpl)tf);
		
		TypeLink tl1 = TypeLinkFactory.createLink();
		tl1.setName( "link1" );
		fl1.setType(tl1);
		
		TypeLink tl2 = TypeLinkFactory.createLink();
		tl2.setName( "link2" );
		fl2.setType(tl2);
		
		FactFinder finder = new FactFinder();
		List<Fact> result = finder.find( rootf, "link1", "link2" );
		
		assertNotNull( result );
		assertEquals( 1, result.size() );
		assertEquals( tf, result.get( 0 ) );
	}
	
	@Test
	public void testNotFound() {

		CompositeType ct = TypeFactory.createCompositeType();
		TextualType tt = TypeFactory.createTextualType();
		
		CompositeFact cf = FactFactory.createComposite();
		cf.assignType( ct );
		TextualFact tf = FactFactory.createTextual();
		tf.assignType( tt );
		
		FactLinkFactory flf = new FactLinkFactory();
		FactLink fl = flf.insertLink((CompositeFactImpl)cf, (TextualFactImpl)tf);
		
		TypeLink tl = TypeLinkFactory.createLink();
		tl.setName( "link1" );
		fl.setType(tl);
		
		FactFinder finder = new FactFinder();
		List<Fact> result = finder.find( cf, "link2" );
		
		assertNotNull( result );
		assertTrue( result.isEmpty() );
	}
	
	
	@Test
	public void testNull() {
		FactFinder finder = new FactFinder();
		
		assertNull( finder.find( null, (String[]) null ) );
		assertNull( finder.find( null, "link1" ) );
	}
	
	@Test
	public void testAmbiguousSelector() {

		CompositeType ct = TypeFactory.createCompositeType();
		TextualType tt = TypeFactory.createTextualType();
		
		CompositeFact cf = FactFactory.createComposite();
		cf.assignType( ct );
		TextualFact tf1 = FactFactory.createTextual();
		tf1.assignType( tt );
		TextualFact tf2 = FactFactory.createTextual();
		tf2.assignType( tt );
		
		FactLinkFactory flf = new FactLinkFactory();
		FactLink fl1 = flf.insertLink((CompositeFactImpl)cf, (TextualFactImpl)tf1);
		FactLink fl2 = flf.insertLink((CompositeFactImpl)cf, (TextualFactImpl)tf2);

		TypeLink tl = TypeLinkFactory.createLink();
		tl.setName( "link1" );
		fl1.setType(tl);
		fl2.setType(tl);
		
		FactFinder finder = new FactFinder();
		List<Fact> result = finder.find( cf, "link1" );
		
		assertNotNull( result );
		assertEquals( 2, result.size() );
		assertTrue( result.contains( tf1 ) );
		assertTrue( result.contains( tf2 ) );
	}
}
