package it.unifi.ing.stlab.reflection.lite.converter.jaxb;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitFactory;
import it.unifi.ing.stlab.reflection.factory.types.UnitUseFactory;
import it.unifi.ing.stlab.reflection.lite.converter.FactConverter;
import it.unifi.ing.stlab.reflection.lite.converter.dao.FactConverterDao;
import it.unifi.ing.stlab.reflection.lite.factory.FactFactory;
import it.unifi.ing.stlab.reflection.lite.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
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
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.Unit;
import it.unifi.ing.stlab.reflection.model.types.UnitUse;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;

import org.junit.Before;
import org.junit.Test;

public class JaxbConverterTest  {

	private FactConverterDao dao;
	private FactConverter converter;
	

	@Before
	public void setUp() throws Exception {
		dao = mock( FactConverterDao.class ); 
		converter = new JaxbConverter( dao );
	}

	
	private void verifyXmlToReflection(  Fact f ) {
		String xml = converter.serialize( f );
		assertNotNull( xml );
		
		Fact generated = converter.deserialize( xml );
		assertNotNull( generated );
		
		assertEquals( xml, converter.serialize( generated ));
	}
	
	@Test
	public void testTextualToXml() {
		TextualType tt = TypeFactory.createTextualType();
		TextualFact tf = FactFactory.createTextual();
		tf.assignType( tt );
		tf.setText( "some text" );
		
		assertEquals( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><textual type=\"" + tt.getUuid() + "\" xmlns=\"http://www.stlab.dsi.unifi.it/reflection/facts\">some text</textual>", converter.serialize( tf ));
	
		tf.setText( null );
		assertEquals( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><textual type=\"" + tt.getUuid() + "\" xmlns=\"http://www.stlab.dsi.unifi.it/reflection/facts\"/>", converter.serialize( tf ));
	}
	
	@Test
	public void testTemporalToXml() {
		TemporalType tt = TypeFactory.createTemporalType();
		TemporalFact tf = FactFactory.createTemporal();
		tf.assignType( tt );
		
		tf.setDate( DateHelper.createDate( "2014-06-16" ));
		assertEquals( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><temporal type=\"" + tt.getUuid() + "\" xmlns=\"http://www.stlab.dsi.unifi.it/reflection/facts\">2014-06-16T00:00:00.000+02:00</temporal>", converter.serialize( tf ));

		tf.setDate( null );
		assertEquals( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><temporal type=\"" + tt.getUuid() + "\" xmlns=\"http://www.stlab.dsi.unifi.it/reflection/facts\"/>", converter.serialize( tf ));
	}

	@Test
	public void testQuantitativeToXml() {
		Unit um = UnitFactory.createUnit();
		UnitUse uu = UnitUseFactory.createUnitUse();
		uu.setUnit( um );
		QuantitativeType qt = TypeFactory.createQuantitativeType();
		qt.addUnit( uu );
		
		QuantitativeFact qf = FactFactory.createQuantitative();
		qf.assignType( qt );
		qf.setQuantity( new Quantity() );
		qf.getQuantity().setUnit( um );
		qf.getQuantity().setValue( 100.0 );

		assertEquals( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><quantitative type=\"" + qt.getUuid() + "\" um=\"" + um.getUuid() + "\" xmlns=\"http://www.stlab.dsi.unifi.it/reflection/facts\">100.0</quantitative>", converter.serialize( qf ));
	
		qf.getQuantity().setValue( null );
		assertEquals( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><quantitative type=\"" + qt.getUuid() + "\" um=\"" + um.getUuid() + "\" xmlns=\"http://www.stlab.dsi.unifi.it/reflection/facts\"/>", converter.serialize( qf ));
	}

	@Test
	public void testEnumeratedToXml() {
		Phenomenon ph = PhenomenonFactory.createPhenomenon();
		EnumeratedType et = TypeFactory.createEnumeratedType();
		et.addPhenomenon( ph );
	
		QualitativeFact qf = FactFactory.createQualitative();
		qf.assignType( et );
		qf.setPhenomenon( ph );

		assertEquals( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><qualitative type=\"" + et.getUuid() + "\" xmlns=\"http://www.stlab.dsi.unifi.it/reflection/facts\">" + ph.getUuid() + "</qualitative>", converter.serialize( qf ));
	
		qf.setPhenomenon( null );
		assertEquals( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><qualitative type=\"" + et.getUuid() + "\" xmlns=\"http://www.stlab.dsi.unifi.it/reflection/facts\"/>", converter.serialize( qf ));
	}

	@Test
	public void testQueriedToXml() {
		Phenomenon ph = PhenomenonFactory.createPhenomenon();
		QueriedType qt = TypeFactory.createQueriedType();
	
		QualitativeFact qf = FactFactory.createQualitative();
		qf.assignType( qt );
		qf.setPhenomenon( ph );

		assertEquals( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><qualitative type=\"" + qt.getUuid() + "\" xmlns=\"http://www.stlab.dsi.unifi.it/reflection/facts\">" + ph.getUuid() + "</qualitative>", converter.serialize( qf ));
	
		qf.setPhenomenon( null );
		assertEquals( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><qualitative type=\"" + qt.getUuid() + "\" xmlns=\"http://www.stlab.dsi.unifi.it/reflection/facts\"/>", converter.serialize( qf ));
	}
	
	@Test
	public void testCompositeToXml1() {
		CompositeType ct = TypeFactory.createCompositeType();
		TextualType tt = TypeFactory.createTextualType();

		TypeLink tl = TypeLinkFactory.createLink();
		tl.assignSource( ct );
		tl.assignTarget( tt );
		

		CompositeFact cf = FactFactory.createComposite();
		cf.assignType( ct );
		
		TextualFact tf = FactFactory.createTextual();
		tf.assignType( tt );
		tf.setText( "some text" );

		new FactLinkFactory();
		FactLink fl = FactLinkFactory.insertLink( cf, tf );
		fl.setType( tl );

		assertEquals( 
			"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
			"<composite type=\"" + ct.getUuid() + "\" xmlns=\"http://www.stlab.dsi.unifi.it/reflection/facts\">" +
				"<textual type=\"" + tl.getUuid() + "\">some text</textual>" + 
			"</composite>", 
			converter.serialize( cf ));
	}
	
	@Test
	public void testCompositeToXml2() {
		CompositeType ct = TypeFactory.createCompositeType();
		Phenomenon ph = PhenomenonFactory.createPhenomenon();
		QueriedType qt = TypeFactory.createQueriedType();
	
		TypeLink tl = TypeLinkFactory.createLink();
		tl.assignSource( ct );
		tl.assignTarget( qt );
		
		CompositeFact cf = FactFactory.createComposite();
		cf.assignType( ct );
		
		QualitativeFact qf = FactFactory.createQualitative();
		qf.assignType( qt );
		qf.setPhenomenon( ph );

		new FactLinkFactory();
		FactLink fl = FactLinkFactory.insertLink( cf, qf );
		fl.setType( tl );
		
		assertEquals( 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
				"<composite type=\"" + ct.getUuid() + "\" xmlns=\"http://www.stlab.dsi.unifi.it/reflection/facts\">" +
					"<qualitative type=\"" + tl.getUuid() + "\">" +  ph.getUuid() + "</qualitative>" + 
				"</composite>", 
				converter.serialize( cf ));
	}
	
	@Test
	public void testCompositeToXml3() {
		CompositeType ct1 = TypeFactory.createCompositeType();
		CompositeType ct2 = TypeFactory.createCompositeType();
		TextualType tt = TypeFactory.createTextualType();

		TypeLink tl1 = TypeLinkFactory.createLink();
		tl1.assignSource( ct1 );
		tl1.assignTarget( ct2 );
		
		TypeLink tl2 = TypeLinkFactory.createLink();
		tl2.assignSource( ct2 );
		tl2.assignTarget( tt );

		CompositeFact cf1 = FactFactory.createComposite();
		cf1.assignType( ct1 );
		
		CompositeFact cf2 = FactFactory.createComposite();
		cf2.assignType( ct2 );

		new FactLinkFactory();
		FactLink fl1 = FactLinkFactory.insertLink( cf1, cf2 );
		fl1.setType( tl1 );
		
		
		TextualFact tf = FactFactory.createTextual();
		tf.assignType( tt );
		tf.setText( "some text" );

		new FactLinkFactory();
		FactLink fl2 = FactLinkFactory.insertLink( cf2, tf );
		fl2.setType( tl2 );

		assertEquals( 
				"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
				"<composite type=\"" + ct1.getUuid() + "\" xmlns=\"http://www.stlab.dsi.unifi.it/reflection/facts\">" +
					"<composite type=\"" + tl1.getUuid() + "\">" + 
				    	"<textual type=\"" +  tl2.getUuid() + "\">some text</textual>" +
					"</composite>" +
				"</composite>", 
				converter.serialize( cf1 ));
	}
	
	
	@Test
	public void testXmlToTextual() {
		TextualType tt = TypeFactory.createTextualType();
		TextualFact tf = FactFactory.createTextual();
		tf.assignType( tt );
		tf.setText( "some text" );
		
		when( dao.findType( eq( tt.getUuid() ))).thenReturn( tt );
		
		verifyXmlToReflection( tf );
		
		tf.setText( null );
		verifyXmlToReflection( tf );
	}

	@Test
	public void testXmlToTemporal() {
		TemporalType tt = TypeFactory.createTemporalType();
		TemporalFact tf = FactFactory.createTemporal();
		tf.assignType( tt );
		when( dao.findType( eq( tt.getUuid() ))).thenReturn( tt );
		
		tf.setDate( DateHelper.createDate( "2014-06-16" ));
		verifyXmlToReflection( tf );
		
		tf.setDate( null );
		verifyXmlToReflection( tf );
	}

	@Test
	public void testXmlToQuantitative() {
		Unit um = UnitFactory.createUnit();
		UnitUse uu = UnitUseFactory.createUnitUse();
		uu.setUnit( um );
		QuantitativeType qt = TypeFactory.createQuantitativeType();
		qt.addUnit( uu );
		
		QuantitativeFact qf = FactFactory.createQuantitative();
		qf.assignType( qt );
		qf.setQuantity( new Quantity() );
		qf.getQuantity().setUnit( um );
		qf.getQuantity().setValue( 100.0 );

		when( dao.findType( eq( qt.getUuid() ))).thenReturn( qt );

		verifyXmlToReflection( qf );
		
		qf.getQuantity().setValue( null );
		verifyXmlToReflection( qf );
	}
	
	@Test
	public void testXmlToEnumerated() {
		Phenomenon ph = PhenomenonFactory.createPhenomenon();
		EnumeratedType et = TypeFactory.createEnumeratedType();
		et.addPhenomenon( ph );
	
		QualitativeFact qf = FactFactory.createQualitative();
		qf.assignType( et );
		qf.setPhenomenon( ph );

		when( dao.findType( eq( et.getUuid() ))).thenReturn( et );

		verifyXmlToReflection( qf );
		
		qf.setPhenomenon( null );
		verifyXmlToReflection( qf );
	}
	
	@Test
	public void testXmlToQueried() {
		Phenomenon ph = PhenomenonFactory.createPhenomenon();
		QueriedType qt = TypeFactory.createQueriedType();
	
		QualitativeFact qf = FactFactory.createQualitative();
		qf.assignType( qt );
		qf.setPhenomenon( ph );

		when( dao.findType( eq( qt.getUuid() ))).thenReturn( qt );
		when( dao.findPhenomenon( eq( ph.getUuid() ))).thenReturn( ph );

		verifyXmlToReflection( qf );
		
		qf.setPhenomenon( null );
		verifyXmlToReflection( qf );
	}
	
	@Test
	public void testXmlToComposite1() {
		CompositeType ct = TypeFactory.createCompositeType();
		TextualType tt = TypeFactory.createTextualType();

		TypeLink tl = TypeLinkFactory.createLink();
		tl.assignSource( ct );
		tl.assignTarget( tt );
		

		CompositeFact cf = FactFactory.createComposite();
		cf.assignType( ct );
		
		TextualFact tf = FactFactory.createTextual();
		tf.assignType( tt );
		tf.setText( "some text" );

		new FactLinkFactory();
		FactLink fl = FactLinkFactory.insertLink( cf, tf );
		fl.setType( tl );

		when( dao.findType( eq( ct.getUuid() ))).thenReturn( ct );

		verifyXmlToReflection( cf );
	}
	

	@Test
	public void testXmlToComposite2() {
		CompositeType ct = TypeFactory.createCompositeType();
		TemporalType tt = TypeFactory.createTemporalType();

		TypeLink tl = TypeLinkFactory.createLink();
		tl.assignSource( ct );
		tl.assignTarget( tt );
		

		CompositeFact cf = FactFactory.createComposite();
		cf.assignType( ct );
		
		TemporalFact tf = FactFactory.createTemporal();
		tf.assignType( tt );
		tf.setDate( DateHelper.createDate( "2014-06-16" ));

		new FactLinkFactory();
		FactLink fl = FactLinkFactory.insertLink( cf, tf );
		fl.setType( tl );

		
		when( dao.findType( eq( ct.getUuid() ))).thenReturn( ct );

		verifyXmlToReflection( cf );
	}
	
	@Test
	public void testXmlToComposite3() {
		CompositeType ct = TypeFactory.createCompositeType();
		Unit um = UnitFactory.createUnit();
		UnitUse uu = UnitUseFactory.createUnitUse();
		uu.setUnit( um );
		QuantitativeType qt = TypeFactory.createQuantitativeType();
		qt.addUnit( uu );

		TypeLink tl = TypeLinkFactory.createLink();
		tl.assignSource( ct );
		tl.assignTarget( qt );
		

		CompositeFact cf = FactFactory.createComposite();
		cf.assignType( ct );
		
		QuantitativeFact qf = FactFactory.createQuantitative();
		qf.assignType( qt );
		qf.setQuantity( new Quantity() );
		qf.getQuantity().setUnit( um );
		qf.getQuantity().setValue( 100.0 );

		new FactLinkFactory();
		FactLink fl = FactLinkFactory.insertLink( cf, qf );
		fl.setType( tl );

		
		when( dao.findType( eq( ct.getUuid() ))).thenReturn( ct );

		verifyXmlToReflection( cf );
	}
	
	@Test
	public void testXmlToComposite4() {
		CompositeType ct = TypeFactory.createCompositeType();
		Phenomenon ph = PhenomenonFactory.createPhenomenon();
		EnumeratedType et = TypeFactory.createEnumeratedType();
		et.addPhenomenon( ph );
	
		TypeLink tl = TypeLinkFactory.createLink();
		tl.assignSource( ct );
		tl.assignTarget( et );
		

		CompositeFact cf = FactFactory.createComposite();
		cf.assignType( ct );
		
		QualitativeFact qf = FactFactory.createQualitative();
		qf.assignType( et );
		qf.setPhenomenon( ph );

		new FactLinkFactory();
		FactLink fl = FactLinkFactory.insertLink( cf, qf );
		fl.setType( tl );

		
		when( dao.findType( eq( ct.getUuid() ))).thenReturn( ct );

		verifyXmlToReflection( cf );
	}
	
	@Test
	public void testXmlToComposite5() {
		CompositeType ct = TypeFactory.createCompositeType();
		Phenomenon ph = PhenomenonFactory.createPhenomenon();
		QueriedType qt = TypeFactory.createQueriedType();
	
		TypeLink tl = TypeLinkFactory.createLink();
		tl.assignSource( ct );
		tl.assignTarget( qt );
		
		CompositeFact cf = FactFactory.createComposite();
		cf.assignType( ct );
		
		QualitativeFact qf = FactFactory.createQualitative();
		qf.assignType( qt );
		qf.setPhenomenon( ph );

		new FactLinkFactory();
		FactLink fl = FactLinkFactory.insertLink( cf, qf );
		fl.setType( tl );

		
		when( dao.findType( eq( ct.getUuid() ))).thenReturn( ct );
		when( dao.findPhenomenon( eq( ph.getUuid() ))).thenReturn( ph );

		verifyXmlToReflection( cf );
	}

	@Test
	public void testXmlToComposite6() {
		CompositeType ct1 = TypeFactory.createCompositeType();
		CompositeType ct2 = TypeFactory.createCompositeType();
		TextualType tt = TypeFactory.createTextualType();

		TypeLink tl1 = TypeLinkFactory.createLink();
		tl1.assignSource( ct1 );
		tl1.assignTarget( ct2 );
		
		
		TypeLink tl2 = TypeLinkFactory.createLink();
		tl2.assignSource( ct2 );
		tl2.assignTarget( tt );

		CompositeFact cf1 = FactFactory.createComposite();
		cf1.assignType( ct1 );
		
		CompositeFact cf2 = FactFactory.createComposite();
		cf2.assignType( ct2 );

		new FactLinkFactory();
		FactLink fl1 = FactLinkFactory.insertLink( cf1, cf2 );
		fl1.setType( tl1 );
		
		
		TextualFact tf = FactFactory.createTextual();
		tf.assignType( tt );
		tf.setText( "some text" );

		new FactLinkFactory();
		FactLink fl2 = FactLinkFactory.insertLink( cf2, tf );
		fl2.setType( tl2 );

		when( dao.findType( eq( ct1.getUuid() ))).thenReturn( ct1 );

		verifyXmlToReflection( cf1 );
	}
	
	@Test
	public void testXmlToComposite7() {
		CompositeType ct = TypeFactory.createCompositeType();
		TextualType tt = TypeFactory.createTextualType();

		TypeLink tl = TypeLinkFactory.createLink();
		tl.assignSource( ct );
		tl.assignTarget( tt );
		

		CompositeFact cf = FactFactory.createComposite();
		cf.assignType( ct );
		
		TextualFact tf1 = FactFactory.createTextual();
		tf1.assignType( tt );
		tf1.setText( "some text" );
		
		TextualFact tf2 = FactFactory.createTextual();
		tf2.assignType( tt );
		tf2.setText( "some other text" );

		new FactLinkFactory();
		FactLink fl1 = FactLinkFactory.insertLink( cf, tf1 );
		fl1.setPriority( 0l );
		fl1.setType( tl );
		
		new FactLinkFactory();
		FactLink fl2 = FactLinkFactory.insertLink( cf, tf2 );
		fl1.setPriority( 1l );
		fl2.setType( tl );

		when( dao.findType( eq( ct.getUuid() ))).thenReturn( ct );

		verifyXmlToReflection( cf );
	}
	
}
