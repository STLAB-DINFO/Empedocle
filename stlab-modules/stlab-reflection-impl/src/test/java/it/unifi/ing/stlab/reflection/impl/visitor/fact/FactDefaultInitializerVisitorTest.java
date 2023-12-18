package it.unifi.ing.stlab.reflection.impl.visitor.fact;

import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitUseFactory;
import it.unifi.ing.stlab.reflection.impl.visitor.type.FactRandomFactoryVisitor;
import it.unifi.ing.stlab.reflection.impl.visitor.type.SampleFactFactoryVisitor;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;
import it.unifi.ing.stlab.reflection.visitor.fact.FactDefaultInitializerVisitor;

import org.junit.Before;
import org.junit.Test;

public class FactDefaultInitializerVisitorTest {
	
	private FactDefaultInitializerVisitor visitor;
	
	@Before
	public void setUp() {
		visitor = new FactDefaultInitializerVisitor();
	}
	
	@Test
	public void testOsservazioneQuantitativa() {
		QuantitativeType qnt = TypeFactory.createQuantitativeType();
		UnitUse uu1 = UnitUseFactory.createUnitUse();
		UnitUse uu2 = UnitUseFactory.createUnitUse();
		Unit u1 = UnitFactory.createUnit();
		Unit u2 = UnitFactory.createUnit();
		
		uu1.setDigits(5);
		uu1.setDecimals(2);
		uu1.setUnit(u1);
		uu2.setDigits(3);
		uu2.setDecimals(1);
		uu2.setUnit(u2);
		
		qnt.addUnit(uu1);
		qnt.addUnit(uu2);
		
		QuantitativeFact oss = (QuantitativeFact)createFact(qnt);
		oss.setQuantity(null);
		oss.accept( visitor );
		
		assertNotNull(oss.getQuantity());
		assertNotNull(oss.getQuantity().getUnit());
	}
	
	private Fact createFact( Type type ) {
		SampleFactFactoryVisitor factory = new FactRandomFactoryVisitor();
		type.accept( factory );
		Fact result = factory.getFact();
		return result;
	}

}
