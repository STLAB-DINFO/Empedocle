package it.unifi.ing.stlab.empedocle.actions.health.examination.statistics.export.visitor;

import it.unifi.ing.stlab.commons.util.TimeFormat;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactInsertLink;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactLinkImpl;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import org.junit.Before;

public class BaseStatisticsTest {
	
	protected CompositeType simpleCompositeType;
	protected CompositeType complexCompositeType;
	protected TextualType textualType;
	protected TextualType anotherTextualType;
	protected EnumeratedType enumType;
	protected QuantitativeType qntType;
	protected TemporalType tempType;
		
	protected CompositeFact simpleCompositeFact;
	protected CompositeFact complexCompositeFact;
	protected TextualFact textualFact;
	protected TextualFact anotherTextualFact;
	protected QualitativeFact qltFact;
	protected QuantitativeFact qntFact;
	protected TemporalFact tempFact;
	
	
	@Before
	public void setUp() throws Exception {
		tempType = TypeFactory.createTemporalType();
		tempFact = FactFactory.createTemporal();
		
		tempType.setType(TimeFormat.DATE);
		tempFact.assignType(tempType);
		tempType.setName("Temporal Type");
		
		enumType = TypeFactory.createEnumeratedType();
		qltFact = FactFactory.createQualitative();
		Phenomenon ph = PhenomenonFactory.createPhenomenon();
		ph.setName("phen");
		enumType.addPhenomenon(ph);
		enumType.setName("Enum Type");
		qltFact.setPhenomenon(ph);
		qltFact.assignType(enumType);
		
		qntType = TypeFactory.createQuantitativeType();
		qntFact = FactFactory.createQuantitative();
		Unit u = UnitFactory.createUnit();
		u.setName("unita_misura");
		u.setSimbol("u");
		Quantity q = new Quantity(10.0, u);
		
		qntType.setName("Quantitative Type");
		qntFact.setQuantity(q);
		qntFact.assignType(qntType);
		
		simpleCompositeType = TypeFactory.createCompositeType();
		simpleCompositeType.setName( "Composite type" );
		
		complexCompositeType = TypeFactory.createCompositeType();
		complexCompositeType.setName("Complex composite type");
		
		textualType = TypeFactory.createTextualType();
		textualType.setName( "Textual type" );
		
		anotherTextualType = TypeFactory.createTextualType();
		anotherTextualType.setName("Another textual type");
		
		TypeLink typeLink = TypeLinkFactory.createLink();
		typeLink.assignSource( simpleCompositeType );
		typeLink.assignTarget( textualType );
		typeLink.setMin(0);
		typeLink.setMax(2);
		typeLink.setName("TextualInsideSimple");
		typeLink.setPriority(10L);
		
		TypeLink typeLink2 = TypeLinkFactory.createLink();
		typeLink2.assignSource(complexCompositeType);
		typeLink2.assignTarget(anotherTextualType);
		typeLink2.setMin(0);
		typeLink2.setMax(3);
		typeLink2.setName("TextualInsideComplex");
		typeLink2.setPriority(20L);
		
		TypeLink typeLink3 = TypeLinkFactory.createLink();
		typeLink3.assignSource(complexCompositeType);
		typeLink3.assignTarget(simpleCompositeType);
		typeLink3.setMin(1);
		typeLink3.setMax(1);
		typeLink3.setName("SimpleInsideComplex");
		typeLink3.setPriority(30L);
	
		simpleCompositeFact = FactFactory.createComposite();
		simpleCompositeFact.assignType( simpleCompositeType );
		
		complexCompositeFact = FactFactory.createComposite();
		complexCompositeFact.assignType(complexCompositeType);
	
		textualFact = FactFactory.createTextual();
		textualFact.assignType( textualType );
		textualFact.setText("text");
		
		anotherTextualFact = FactFactory.createTextual();
		anotherTextualFact.assignType(anotherTextualType);
		anotherTextualFact.setText("other text");
		
		TextualFact brother = FactFactory.createTextual();
		brother.assignType(anotherTextualType);
		brother.setText("brother's text");
		
		FactLinkFactory factLinkFactory = new FactLinkFactory();
		FactLink fl = (FactInsertLink) factLinkFactory.insertLink((FactImpl)simpleCompositeFact, (FactImpl)textualFact);
		fl.setType(typeLink);
		
		FactLink fl1 = (FactInsertLink) factLinkFactory.insertLink((FactImpl)complexCompositeFact, (FactImpl)anotherTextualFact);
		fl1.setType(typeLink2);
		
		FactLink fl1_2 = (FactInsertLink) factLinkFactory.insertLink((FactImpl)complexCompositeFact, (FactImpl)brother);
		fl1_2.setType(typeLink2);
		
		FactLinkImpl fl2 = (FactInsertLink) factLinkFactory.insertLink((FactImpl)complexCompositeFact, (FactImpl)simpleCompositeFact);
		fl2.setType(typeLink3);
		fl2.setPriority(1L);
		
	}

}
