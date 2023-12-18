package it.unifi.ing.stlab.view.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.view.factory.ViewerFactory;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.widgets.container.ConditionalPanel;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.input.InputList;
import it.unifi.ing.stlab.view.model.widgets.output.OutputList;

public class ConditionalPanelControllerTest {

	protected ConditionalPanelController controller;
	protected ConditionalPanel conditionalPanel;
	protected FactLinkFactory linkFactory;
	protected Phenomenon f1, f2, f3;
	protected TypeSelector sel1, sel2, sel3, clearSelector;
	
	@Before
	public void setUp(){
		controller = new ConditionalPanelController();
		conditionalPanel = new ConditionalPanel(UUID.randomUUID().toString());
		conditionalPanel.setClear(false);
		
		linkFactory = new FactLinkFactory();
		
		f1 = PhenomenonFactory.createPhenomenon();
		f1.setName("Si");
		f2 = PhenomenonFactory.createPhenomenon();
		f2.setName("Si");
		f3 = PhenomenonFactory.createPhenomenon();
		f3.setName("Si");
		
		sel1 = mock(TypeSelector.class);
		sel2 = mock(TypeSelector.class);
		sel3 = mock(TypeSelector.class);
		clearSelector = mock(TypeSelector.class);
	}
	

	@Test
	public void test(){
		conditionalPanel.addFenomeno( f1 );

		CompositeFact oc = FactFactory.createComposite();
		QualitativeFact oq = FactFactory.createQualitative();
		oq.setPhenomenon( f1 );
		
		TypeLink tl = TypeLinkFactory.createLink();
		Type t = TypeFactory.createEnumeratedType();
		oq.assignType( t );
		tl.assignTarget( t );
		
		when( sel1.getLast() ).thenReturn( sel1 );
		when( sel1.getTypeLink() ).thenReturn( tl );
		when( sel1.apply( oc ) ).thenReturn( oq );
		conditionalPanel.addSelector( sel1 );
		
		assertEquals("@none", controller.render( t, ViewerFactory.createCombo() ));
		
		controller.checkRendered( conditionalPanel, oc );
		
		assertEquals("@form", controller.render( t, ViewerFactory.createCombo() ));
		
	}
	
	
	@Test
	public void testCheckRendered(){
		conditionalPanel.addFenomeno( f1 );

		CompositeFact oc = FactFactory.createComposite();
		QualitativeFact oq = FactFactory.createQualitative();
		oq.setPhenomenon( f1 );
		
		TypeLink tl = TypeLinkFactory.createLink();
		Type t = TypeFactory.createEnumeratedType();
		
		oq.assignType( t );
		tl.assignTarget( t );
		
		FactLinkFactory flf = new FactLinkFactory();
		FactLink fl = flf.insertLink( oc, oq );
		fl.setType( tl );

		when( sel1.getLast() ).thenReturn( sel1 );
		when( sel1.getTypeLink() ).thenReturn( tl );
		when( sel1.applyItem( oc ) ).thenReturn( Collections.singleton( fl ) );

		conditionalPanel.addSelector( sel1 );
		
		assertTrue( controller.checkRendered( conditionalPanel, oc ) );
		
		oq.setPhenomenon( f2 );
		controller.render( t, ViewerFactory.createCombo() );
		
		assertFalse( controller.checkRendered( conditionalPanel, oc ) );
	}
	
	
	@Test
	public void testCheckRenderedMoreCondition(){
		conditionalPanel.addFenomeno( f1 );
		conditionalPanel.addFenomeno( f2 );
		
		CompositeFact oc = FactFactory.createComposite();
		QualitativeFact oq1 = FactFactory.createQualitative();
		oq1.setPhenomenon( f1 );
		QualitativeFact oq2 = FactFactory.createQualitative();
		oq2.setPhenomenon( f2 );
		
		TypeLink tl1 = TypeLinkFactory.createLink();
		Type t1 = TypeFactory.createEnumeratedType();
		oq1.assignType( t1 );
		tl1.assignTarget( t1 );
		TypeLink tl2 = TypeLinkFactory.createLink();
		Type t2 = TypeFactory.createEnumeratedType();
		oq1.assignType( t2 );
		tl2.assignTarget( t2 );
		
		FactLinkFactory flf = new FactLinkFactory();
		FactLink fl1 = flf.insertLink( oc, oq1 );
		fl1.setType( tl1 );
		
		FactLink fl2 = flf.insertLink( oc, oq2 );
		fl2.setType( tl2 );
		
		when( sel1.getLast() ).thenReturn( sel1 );
		when( sel1.getTypeLink() ).thenReturn( tl1 );
		when( sel1.applyItem( oc ) ).thenReturn( Collections.singleton( fl1 ) );
		
		when( sel2.getLast() ).thenReturn( sel2 );
		when( sel2.getTypeLink() ).thenReturn( tl2 );
		when( sel2.applyItem( oc ) ).thenReturn( Collections.singleton( fl2 ) );
		
		conditionalPanel.addSelector( sel1 );
		conditionalPanel.addSelector( sel2 );
		conditionalPanel.addOperator("AND");
		
		assertTrue( controller.checkRendered( conditionalPanel, oc ) );
		
		oq1.setPhenomenon( null );
		controller.render( t1, ViewerFactory.createCombo() );
		
		assertFalse( controller.checkRendered( conditionalPanel, oc ) );
		
	}	
	
	
	@Test
	public void testNull(){
		CompositeFact oc = FactFactory.createComposite();

		assertFalse( controller.checkRendered( null, oc ) );
		assertFalse( controller.checkRendered( conditionalPanel, null ) );
	}
	
	@Test
	public void testOneSelectorClear(){
		conditionalPanel.addFenomeno( f1 );

		CompositeFact oc = FactFactory.createComposite();
		QualitativeFact oq = FactFactory.createQualitative();
		oq.setPhenomenon( f2 );
		
		TypeLink tl = TypeLinkFactory.createLink();
		Type t = TypeFactory.createEnumeratedType();
		oq.assignType( t );
		tl.assignTarget( t );
		
		FactLinkFactory factory = new FactLinkFactory();
		FactLink fl = factory.insertLink( oc, oq );
		fl.setType( tl );
		
		when( sel1.getLast() ).thenReturn( sel1 );
		when( sel1.getTypeLink() ).thenReturn( tl );
		when( sel1.applyItem( oc ) ).thenReturn( Collections.singleton( fl ) );
		conditionalPanel.addSelector( sel1 );
		conditionalPanel.setClear( true );
		conditionalPanel.setClearSelector( sel1 );
		
		assertFalse( oq.isEmpty() );
		assertFalse( controller.checkRendered( conditionalPanel, oc ) );
		assertNull( oq.getPhenomenon() );
		assertTrue( oq.isEmpty() );
	}
	
	@Test
	public void testOneSelectorClearWithMultiplicity(){
		conditionalPanel.addFenomeno( f1 );

		CompositeFact oc = FactFactory.createComposite();
		QualitativeFact oq = FactFactory.createQualitative();
		oq.setPhenomenon( f2 );
		
		TypeLink tl = TypeLinkFactory.createLink();
		Type t = TypeFactory.createEnumeratedType();
		oq.assignType( t );
		tl.assignTarget( t );
		
		QualitativeFact oq2 = FactFactory.createQualitative();
		oq2.setPhenomenon( f2 );
		oq2.assignType( t );
		
		FactLinkFactory factory = new FactLinkFactory();
		FactLink fl = factory.insertLink( oc, oq );
		fl.setType( tl );
		FactLink fl2 = factory.insertLink( oc, oq2 );
		fl2.setType( tl );
		
		when( sel1.getLast() ).thenReturn( sel1 );
		when( sel1.getTypeLink() ).thenReturn( tl );
		when( sel1.applyItem( oc ) ).thenReturn( ImmutableSet.of( fl, fl2 ) );
		conditionalPanel.addSelector( sel1 );
		conditionalPanel.setClear( true );
		conditionalPanel.setClearSelector( sel1 );
		
		assertFalse( oq.isEmpty() );
		assertFalse( oq2.isEmpty() );
		assertFalse( controller.checkRendered( conditionalPanel, oc ) );
		assertNull( oq.getPhenomenon() );
		assertTrue( oq.isEmpty() );
		assertNull( oq2.getPhenomenon() );
		assertTrue( oq2.isEmpty() );
	}
	
	@Test
	public void testMultiSelectorClear(){
		conditionalPanel.addFenomeno( f1 );

		CompositeFact oc = FactFactory.createComposite();
		QualitativeFact oq = FactFactory.createQualitative();
		oq.setPhenomenon( f2 );
		
		TypeLink tl = TypeLinkFactory.createLink();
		Type t = TypeFactory.createEnumeratedType();
		oq.assignType( t );
		tl.assignTarget( t );
		
		QualitativeFact oq2 = FactFactory.createQualitative();
		oq2.setPhenomenon( f2 );
		oq2.assignType( t );
		
		TypeLink tl2 = TypeLinkFactory.createLink();
		oq2.assignType( t );
		tl2.assignTarget( t );
		
		FactLinkFactory factory = new FactLinkFactory();
		FactLink fl = factory.insertLink( oc, oq );
		FactLink fl2 = factory.insertLink( oc, oq2 );
		
		when( sel1.getLast() ).thenReturn( sel1 );
		when( sel1.getTypeLink() ).thenReturn( tl );
		when( sel1.applyItem( oc ) ).thenReturn( Collections.singleton( fl ) );
		
		when( sel2.getLast() ).thenReturn( sel2 );
		when( sel2.getTypeLink() ).thenReturn( tl2 );
		when( sel2.applyItem( oc ) ).thenReturn( Collections.singleton( fl2 ) );
		
		conditionalPanel.addSelector( sel1 );
		conditionalPanel.setClear( true );
		conditionalPanel.addClearSelector( sel1 );
		conditionalPanel.addClearSelector( sel2 );
		
		assertFalse( oq.isEmpty() );
		assertFalse( oq2.isEmpty() );
		assertFalse( controller.checkRendered( conditionalPanel, oc ) );
		assertNull( oq.getPhenomenon() );
		assertTrue( oq.isEmpty() );
		assertNull( oq2.getPhenomenon() );
		assertTrue( oq2.isEmpty() );
	}
	
	@Test
	public void testIsVistaList1(){
		Viewer v = new InputList(UUID.randomUUID().toString());
		
		assertTrue(controller.isVistaCompositaItem(v));
	}
	
	@Test
	public void testIsVistaList2(){
		Viewer v = new OutputList(UUID.randomUUID().toString());
		
		assertTrue(controller.isVistaCompositaItem(v));
	}
	
	@Test
	public void testIsVistaListFail(){
		Viewer v = new Grid(UUID.randomUUID().toString());
		
		assertFalse(controller.isVistaCompositaItem(v));
	}
	
}
