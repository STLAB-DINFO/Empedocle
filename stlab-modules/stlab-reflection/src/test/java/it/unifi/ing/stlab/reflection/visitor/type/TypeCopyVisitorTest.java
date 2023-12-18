package it.unifi.ing.stlab.reflection.visitor.type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.Type;

import org.junit.Test;

public class TypeCopyVisitorTest extends BasicTypeTest {
	
	protected TypeCopyVisitor visitor;
	
	@Override
	public void setUp() {
		super.setUp();
		
		visitor = new TypeCopyVisitor();
		
	}
	
	@Test
	public void testNameNull() {
		textualType.setName(null);
		
		textualType.accept(visitor);
		
		assertNull(visitor.getResult().getName());

	}
	
	@Test
	public void testTextual() {
		textualType.accept(visitor);
		
		basicTest(textualType, visitor.getResult());
		
	}
	
	@Test
	public void testEnumerated() {
		enumType.accept(visitor);
		
		basicTest(enumType, visitor.getResult());
		
		assertEquals(1, enumType.listPhenomena().size());
		assertEquals(enumType.listPhenomena().iterator().next().getName(),
				((EnumeratedType)visitor.getResult()).listPhenomena().iterator().next().getName());
		assertEquals(enumType.listPhenomena().iterator().next().getPosition(),
				((EnumeratedType)visitor.getResult()).listPhenomena().iterator().next().getPosition());
		assertEquals(enumType.listPhenomena().iterator().next().getTimeRange(),
				((EnumeratedType)visitor.getResult()).listPhenomena().iterator().next().getTimeRange());
		
	}
	
	@Test
	public void testQueried() {
		queriedType.accept(visitor);
		
		basicTest(queriedType, visitor.getResult());
		assertEquals(queriedType.getQueryDef(), ((QueriedType)visitor.getResult()).getQueryDef());
		
	}
	
	@Test
	public void testQuantitative() {
		qtType.accept(visitor);
		
		basicTest(qtType, visitor.getResult());
		assertEquals(1, ((QuantitativeType)visitor.getResult()).listUnits().size());
		assertEquals(qtType.listUnits().iterator().next().getDecimals(),
				((QuantitativeType)visitor.getResult()).listUnits().iterator().next().getDecimals());
		assertEquals(qtType.listUnits().iterator().next().getDigits(),
				((QuantitativeType)visitor.getResult()).listUnits().iterator().next().getDigits());
		assertEquals(qtType.listUnits().iterator().next().getUnit(),
				((QuantitativeType)visitor.getResult()).listUnits().iterator().next().getUnit());
		
	}
	
	@Test
	public void testCompositeSimple() {
		compType.accept(visitor);
		
		basicTest(compType, visitor.getResult());
		
		assertEquals(1, visitor.getResult().listChildren().size());
		assertEquals(compType.listChildren().iterator().next().getName(),
				visitor.getResult().listChildren().iterator().next().getName());
		assertEquals(compType.listChildren().iterator().next().getMax(),
				visitor.getResult().listChildren().iterator().next().getMax());
		assertEquals(compType.listChildren().iterator().next().getMin(),
				visitor.getResult().listChildren().iterator().next().getMin());
		assertEquals(compType.listChildren().iterator().next().getPriority(),
				visitor.getResult().listChildren().iterator().next().getPriority());
		assertTrue(textualType == visitor.getResult().listChildren().iterator().next().getTarget());
		
	}
	
	@Test
	public void testCompositeAnonimous() {
		anonymousCmpType.accept(visitor);
		
		basicTest(anonymousCmpType, visitor.getResult());
		
		assertEquals(1, visitor.getResult().listChildren().size());
		assertEquals(anonymousCmpType.listChildren().iterator().next().getName(),
				visitor.getResult().listChildren().iterator().next().getName());
		assertEquals(anonymousCmpType.listChildren().iterator().next().getMax(),
				visitor.getResult().listChildren().iterator().next().getMax());
		assertEquals(anonymousCmpType.listChildren().iterator().next().getMin(),
				visitor.getResult().listChildren().iterator().next().getMin());
		assertEquals(anonymousCmpType.listChildren().iterator().next().getPriority(),
				visitor.getResult().listChildren().iterator().next().getPriority());
		
		assertTrue(anonymousTxtType != visitor.getResult().listChildren().iterator().next().getTarget());
		
		basicTest(anonymousTxtType, visitor.getResult().listChildren().iterator().next().getTarget());
		
	}
	
	private void basicTest(Type source, Type copy) {
		assertNotNull(copy);
		assertNotNull(copy.getUuid());
		assertEquals(source.getAnonymous(), copy.getAnonymous());
		assertEquals(source.getRecurrent(), copy.getRecurrent());
		assertEquals(source.getReadOnly(), copy.getReadOnly());
		assertEquals(source.getName() + " (Copia)", copy.getName());
		assertEquals(source.getDescription(), copy.getDescription());
		assertEquals(source.getTimeRange(), copy.getTimeRange());
		
	}

}
