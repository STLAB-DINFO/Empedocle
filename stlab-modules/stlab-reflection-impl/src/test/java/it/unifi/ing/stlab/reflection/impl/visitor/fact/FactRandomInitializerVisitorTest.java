package it.unifi.ing.stlab.reflection.impl.visitor.fact;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.reflection.dao.types.PhenomenonDao;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitUseFactory;
import it.unifi.ing.stlab.reflection.impl.visitor.type.FactRandomFactoryVisitor;
import it.unifi.ing.stlab.reflection.impl.visitor.type.SampleFactFactoryVisitor;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.reflection.visitor.fact.FactRandomInitializerVisitor;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class FactRandomInitializerVisitorTest {

	protected FactRandomInitializerVisitor visitor;
	
	@Before
	public void setUp() {
		visitor = new FactRandomInitializerVisitor();
	}
	
	@Test
	public void testOsservazioneTestuale() {
		TextualFact oss = (TextualFact)createFact(TypeFactory.createTextualType());
		oss.accept( visitor );
		assertNotNull( oss.getText() );
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
		oss.accept( visitor );
		
		assertNotNull( oss.getQuantity().getUnit() );
		assertNotNull( oss.getQuantity().getValue() );
	}

	@Test
	public void testOsservazioneQualitativa() {
		EnumeratedType qlt = TypeFactory.createEnumeratedType();
		
		Phenomenon f1 = PhenomenonFactory.createPhenomenon();
		Phenomenon f2 = PhenomenonFactory.createPhenomenon();
		Phenomenon f3 = PhenomenonFactory.createPhenomenon();
		f1.setName("fen1");
		f1.setName("fen2");
		f1.setName("fen3");
		
		List<Phenomenon> phen = new LinkedList<Phenomenon>();
		phen.add(f1);
		phen.add(f2);
		phen.add(f3);
		
		QualitativeFact oss = (QualitativeFact)createFact(qlt);
		
		PhenomenonDao phenomenonDao = mock(PhenomenonDao.class);
		when(phenomenonDao.findByFact(eq(oss), any(Date.class))).thenReturn(phen);
		visitor.setPhenomenonDAO(phenomenonDao);
		
		oss.accept( visitor );
		
		assertNotNull(oss.getPhenomenon());
		assertTrue(phen.contains(oss.getPhenomenon()));
	}
	
	@Test
	public void testOsservazioneTemporale() {
		TemporalFact oss = (TemporalFact)createFact(TypeFactory.createTemporalType());
		oss.accept( visitor );
		assertNotNull( oss.getDate() );
	}
	
	@Test
	public void testOsservazioneComposita() {
		CompositeType cmp = TypeFactory.createCompositeType();
		TextualType txt1 = TypeFactory.createTextualType();
		TextualType txt2 = TypeFactory.createTextualType();
		TypeLink tl1 = TypeLinkFactory.createLink();
		TypeLink tl2 = TypeLinkFactory.createLink();
		
		tl1.assignSource(cmp);
		tl1.assignTarget(txt1);
		tl1.setMin(1);
		tl1.setMax(3);
		
		tl2.assignSource(cmp);
		tl2.assignTarget(txt2);
		tl2.setMin(2);
		tl2.setMax(5);
		
		CompositeFact oss = (CompositeFact)createFact(cmp);
		oss.accept( visitor );
		
		for ( FactLink item : oss.listChildren() ) {
			assertNotNull( ((TextualFact)item.getTarget()).getText() );
		}
	}
	
	private Fact createFact( Type type ) {
		SampleFactFactoryVisitor factory = new FactRandomFactoryVisitor();
		type.accept( factory );
		Fact result = factory.getFact();
		return result;
	}
	
}
