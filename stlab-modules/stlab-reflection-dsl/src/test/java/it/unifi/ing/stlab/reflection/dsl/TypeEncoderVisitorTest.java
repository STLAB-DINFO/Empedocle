package it.unifi.ing.stlab.reflection.dsl;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.commons.util.TimeFormat;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitUseFactory;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import org.junit.Before;
import org.junit.Test;

public class TypeEncoderVisitorTest {

	protected TypeEncoderVisitor typeEncoder;
	
	@Before
	public void setUp() {
		typeEncoder = new TypeEncoderVisitor();
	}
	
	@Test
	public void testTextualType() {
		TextualType type = TypeFactory.createTextualType();
		type.accept( typeEncoder );
		assertEquals( "tx", typeEncoder.getResult() );
	}
	
	@Test
	public void testQueriedType() {
		QueriedType type = TypeFactory.createQueriedType();
		type.setQueryDef( "select... from... where..." );
		type.accept( typeEncoder );
		assertEquals( "st { \"select... from... where...\" }", typeEncoder.getResult() );
	}
	
	@Test
	public void testEnumeratedType() {
		EnumeratedType type = TypeFactory.createEnumeratedType();
		
		Phenomenon ph1 = PhenomenonFactory.createPhenomenon();
		ph1.setName( "ph1" );
		type.addPhenomenon( ph1 );

		Phenomenon ph2 = PhenomenonFactory.createPhenomenon();
		ph2.setName( "ph2" );
		type.addPhenomenon( ph2 );

		Phenomenon ph3 = PhenomenonFactory.createPhenomenon();
		ph3.setName( "ph3" );
		type.addPhenomenon( ph3 );

		type.accept( typeEncoder );
		assertEquals( "ql { \"ph1\", \"ph2\", \"ph3\" }", typeEncoder.getResult() );
	}
	
	@Test
	public void testQuantitativeType() {
		QuantitativeType type = TypeFactory.createQuantitativeType();

		Unit u1 = UnitFactory.createUnit();
		u1.setName( "metro" );
		u1.setSimbol( "m" );
		
		UnitUse uu1 = UnitUseFactory.createUnitUse();
		uu1.setDecimals( 3 );
		uu1.setDigits( 5 );
		uu1.setUnit( u1 );
		type.addUnit( uu1 );

		Unit u2 = UnitFactory.createUnit();
		u2.setName( "centimetro" );
		u2.setSimbol( "cm" );
		
		UnitUse uu2 = UnitUseFactory.createUnitUse();
		uu2.setDecimals( 5 );
		uu2.setDigits( 7 );
		uu2.setUnit( u2 );
		type.addUnit( uu2 );

		type.accept( typeEncoder );
		assertEquals( "qt { \"centimetro\"(7,5), \"metro\"(5,3) }", typeEncoder.getResult() );
	}
	
	@Test
	public void testTemporalType() {
		TemporalType type = TypeFactory.createTemporalType();
		type.setType( TimeFormat.DATETIME );

		type.accept( typeEncoder );
		assertEquals( "dt datetime", typeEncoder.getResult() );
	}
	
	@Test
	public void testCompositeType() {
		CompositeType type = TypeFactory.createCompositeType();
		
		Type t1 = TypeFactory.createTextualType();
		TypeLink link1 = TypeLinkFactory.createLink();
		link1.setName( "r1" );
		link1.setMin( 0 );
		link1.setMax( 10 );
		link1.assignSource( type );
		link1.assignTarget( t1 );
		link1.setPriority( 0l );
		
		Type t2 = TypeFactory.createTextualType();
		t2.setName( "Named type" );
		TypeLink link2 = TypeLinkFactory.createLink();
		link2.setName( "r2" );
		link2.setMin( 3 );
		link2.setMax( -1 );
		link2.assignSource( type );
		link2.assignTarget( t2 );
		link2.setPriority( 1l );
		
		Type t3 = TypeFactory.createTextualType();
		TypeLink link3 = TypeLinkFactory.createLink();
		link3.setName( "r3" );
		link3.setMin( 1 );
		link3.setMax( 1 );
		link3.assignSource( type );
		link3.assignTarget( t3 );
		link3.setPriority( 2l );
		
		type.accept( typeEncoder );
		assertEquals( "ct {\r\n\t\"r1\"(0,10): tx,\r\n\t\"r2\"(3,unbounded): \"Named type\",\r\n\t\"r3\": tx\r\n}", typeEncoder.getResult() );
	}
	
}
