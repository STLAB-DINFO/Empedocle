package it.unifi.ing.stlab.reflection.lite.visitor.type;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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

import java.lang.reflect.Constructor;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;


public class SampleFactFactoryVisitorTest {

	protected SampleFactFactoryVisitor visitor;
	
	@Before
	public void setUp() {
		visitor = new SampleFactFactoryVisitor();
	}
	
	@Test
	public void testTextual() throws Exception {
		validate( TextualType.class, TextualFact.class );
	}

	@Test
	public void testQuantitative() throws Exception {
		validate( QuantitativeType.class, QuantitativeFact.class );
	}

	@Test
	public void testQueried() throws Exception {
		validate( QueriedType.class, QualitativeFact.class );
	}

	@Test
	public void testEnumerated() throws Exception {
		validate( EnumeratedType.class, QualitativeFact.class );
	}

	@Test
	public void testTemporal() throws Exception {
		validate( TemporalType.class, TemporalFact.class );
	}
	
	@Test
	public void testComposite() throws Exception {
		validate( CompositeType.class, CompositeFact.class );
	}
	
	private void validate(Class<? extends Type> type, Class<? extends Fact> fact ) throws Exception {
		try {
			Constructor<? extends Type> constructor = type.getConstructor( String.class );
			Type newType = constructor.newInstance( UUID.randomUUID().toString() );
			newType.accept( visitor );
			
			Fact createdFact = visitor.getFact();
			assertNotNull( createdFact );
			assertTrue( fact.isInstance( createdFact ));
			assertTrue( newType == createdFact.getType() );
			
		} catch (Exception e) {
			throw new RuntimeException( e );
		}
	}
}
