package it.unifi.ing.stlab.reflection.impl.model.facts;

import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import org.junit.Test;

public class FatherTest {
	protected String uuid;
	protected TimeRange period;
	protected FactManager factDao;
	
	
	@Test
	public void testCompositeFatherFact() {
		CompositeFact cf = FactFactory.createComposite();
		TextualFact tf = FactFactory.createTextual();
		
		FactLinkFactory flf = new FactLinkFactory();
		
		flf.insertLink(cf, tf);
	}
	
	//XXX aggiungere un controllo come nei tipi
	@Test
	public void testTextualFatherFact() {
		
		TextualFact tf = FactFactory.createTextual();
		TextualFact tf2 = FactFactory.createTextual();
		
		FactLinkFactory flf = new FactLinkFactory();
		
		flf.insertLink(tf, tf2);
	}
	
	@Test
	public void testCompositeFatherType() {
		CompositeType cf = TypeFactory.createCompositeType();
		TextualType tf = TypeFactory.createTextualType();
		
		TypeLink tl = TypeLinkFactory.createLink();
		tl.assignSource(cf);
		tl.assignTarget(tf);
	}
	
	@Test( expected = IllegalArgumentException.class )
	public void testTextualFatherType() {
		TextualType tf = TypeFactory.createTextualType();
		TextualType tf2 = TypeFactory.createTextualType();
		
		TypeLink tl = TypeLinkFactory.createLink();
		tl.assignSource(tf);
		tl.assignTarget(tf2);
	}

}
