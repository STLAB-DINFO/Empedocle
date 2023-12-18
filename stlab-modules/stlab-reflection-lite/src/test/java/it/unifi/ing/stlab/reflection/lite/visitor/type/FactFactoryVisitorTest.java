package it.unifi.ing.stlab.reflection.lite.visitor.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import java.lang.reflect.Constructor;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class FactFactoryVisitorTest {

	protected FactFactoryVisitor visitor;
	
	@Before
	public void setUp() {
		visitor = new FactFactoryVisitor();
	}
	
	@Test
	public void testTextual() {
		validate( TextualType.class, TextualFact.class );
	}
	
	@Test
	public void testQuantitative() {
		validate( QuantitativeType.class, QuantitativeFact.class );
	}

	@Test
	public void testQueried() {
		validate( QueriedType.class, QualitativeFact.class );
	}
	
	@Test
	public void testEnumerated() {
		validate( EnumeratedType.class, QualitativeFact.class );
	}
	
	@Test
	public void testTemporal() {
		validate( TemporalType.class, TemporalFact.class );
	}
	
	@Test
	public void testComposite() {
		validate( CompositeType.class, CompositeFact.class );
	}
	
	
	private void validate(Class<? extends Type> type, Class<? extends Fact> factClass )  {
		try {
			Constructor<? extends Type> constructor = type.getConstructor( String.class );
			Type newType = constructor.newInstance( UUID.randomUUID().toString() );
			newType.accept( visitor );
			
			Fact createdFact = visitor.getResult();
			assertNotNull( createdFact );
			assertTrue( factClass.isInstance( createdFact ));
			assertTrue( newType == createdFact.getType() );
			
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
	

	@Test
	public void testZeroChildren() {
		CompositeType compositeType = TypeFactory.createCompositeType();
		TextualType textualType = TypeFactory.createTextualType();
		TypeLink link = TypeLinkFactory.createLink();
		
		link.setMin( 0 );
		link.assignSource( compositeType );
		link.assignTarget( textualType );
	
		compositeType.accept( visitor );
		assertEquals( 0, visitor.getResult().listChildren().size() );
	}
	
	@Test
	public void testOneChildren() {
		CompositeType compositeType = TypeFactory.createCompositeType();
		TextualType textualType = TypeFactory.createTextualType();
		TypeLink link = TypeLinkFactory.createLink();
		
		link.setMin( 1 );
		link.assignSource( compositeType );
		link.assignTarget( textualType );
	
		compositeType.accept( visitor );
		assertEquals( 1, visitor.getResult().listChildren().size() );
	}

	@Test
	public void testNullChildren() {
		CompositeType compositeType = TypeFactory.createCompositeType();
		TextualType textualType = TypeFactory.createTextualType();
		TypeLink link = TypeLinkFactory.createLink();
		
		link.setMin( null );
		link.assignSource( compositeType );
		link.assignTarget( textualType );
	
		compositeType.accept( visitor );
		assertEquals( 0, visitor.getResult().listChildren().size() );
	}
	
	@Test
	public void testFiveChildren() {
		CompositeType compositeType = TypeFactory.createCompositeType();
		TextualType textualType = TypeFactory.createTextualType();
		TypeLink link = TypeLinkFactory.createLink();
		
		link.setMin( 5 );
		link.assignSource( compositeType );
		link.assignTarget( textualType );
	
		compositeType.accept( visitor );
		assertEquals( 5, visitor.getResult().listChildren().size() );
	}
	
}
