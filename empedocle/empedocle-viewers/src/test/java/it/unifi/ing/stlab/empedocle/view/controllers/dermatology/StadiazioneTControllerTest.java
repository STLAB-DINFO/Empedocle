package it.unifi.ing.stlab.empedocle.view.controllers.dermatology;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.empedocle.view.controllers.dermatology.StadiazioneTController;
import it.unifi.ing.stlab.reflection.dao.types.PhenomenonDaoBean;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.model.facts.CompositeFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.QualitativeFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.QuantitativeFactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactInsertLink;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.QuantitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.Quantity;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.view.factory.TypeSelectorFactory;
import it.unifi.ing.stlab.view.factory.ViewerFactory;
import it.unifi.ing.stlab.view.factory.ViewerLinkFactory;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.links.ViewerLink;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StadiazioneTControllerTest {

	protected StadiazioneTController controller;
	protected PhenomenonDaoBean phenomenonDao;
	
	protected FactLinkFactory linkFactory;
	protected CompositeFact parent;
	protected QualitativeFact fact;
	protected TypeSelector selector;
	
	protected Fact root;
	protected QuantitativeFact spessore;
	protected QualitativeFact ulcerazione;
	protected QuantitativeFact mitosi;
	protected QualitativeFact stadiazione;
	
	protected Viewer viewerRoot;
	
	protected List<Phenomenon> stadiazionePhenomena;
	
	@Before
	public void setUp() {
		controller = new StadiazioneTController();
		phenomenonDao = mock(PhenomenonDaoBean.class);
		FieldUtils.assignField( controller, "phenomenonDao", phenomenonDao );
		
		linkFactory = new FactLinkFactory();
		
		initTestData();
	}	

	
	@Test
	public void testRetrieveStadiazione() {
		when(phenomenonDao.findByName(eq(stadiazione), anyString(), any(Date.class))).thenCallRealMethod();
		when(phenomenonDao.findByFact(eq(stadiazione), any(Date.class))).thenReturn(stadiazionePhenomena);
		
		Fact f = controller.retrieveStadiazione( root, viewerRoot );
		assertTrue( f instanceof QualitativeFact );
		assertNull( ((QualitativeFact)f).getPhenomenon() );
		
		// caso T1 A
		
		spessore.getQuantity().setValue( 0.51d );
		mitosi.getQuantity().setValue( 0.95d );
		Phenomenon phen1 = PhenomenonFactory.createPhenomenon();
		phen1.setName( "No" );
		ulcerazione.setPhenomenon( phen1 );
		
		f = controller.retrieveStadiazione( root, viewerRoot );
		assertNotNull( ((QualitativeFact)f).getPhenomenon() );
		assertEquals( "T1 A", ((QualitativeFact)f).getPhenomenon().getName() );
		
		// caso T2 B
		spessore.getQuantity().setValue( 1.01d );
		Phenomenon phen2 = PhenomenonFactory.createPhenomenon();
		phen2.setName( "Si" );
		ulcerazione.setPhenomenon( phen2 );
		
		f = controller.retrieveStadiazione( root, viewerRoot );
		assertNotNull( ((QualitativeFact)f).getPhenomenon() );
		assertEquals( "T2 B", ((QualitativeFact)f).getPhenomenon().getName() );
		
		// caso Non Classificabile
		spessore.getQuantity().setValue( 0.89d );
		mitosi.getQuantity().setValue( 1.07d );
		ulcerazione.setPhenomenon( phen1 );
		
		f = controller.retrieveStadiazione( root, viewerRoot );
		assertNotNull( ((QualitativeFact)f).getPhenomenon() );
		assertEquals( "Non Classificabile", ((QualitativeFact)f).getPhenomenon().getName() );
		
	}
	
	private void initTestData() {
		stadiazionePhenomena = new LinkedList<Phenomenon>();
		
		Phenomenon phen1 = PhenomenonFactory.createPhenomenon();
		phen1.setName( "T1 A" );
		Phenomenon phen2 = PhenomenonFactory.createPhenomenon();
		phen2.setName( "T1 B" );
		Phenomenon phen3 = PhenomenonFactory.createPhenomenon();
		phen3.setName( "T2 A" );
		Phenomenon phen4 = PhenomenonFactory.createPhenomenon();
		phen4.setName( "T2 B" );
		Phenomenon phenNC = PhenomenonFactory.createPhenomenon();
		phenNC.setName( "Non Classificabile" );
		stadiazionePhenomena.add( phen1 );
		stadiazionePhenomena.add( phen2 );
		stadiazionePhenomena.add( phen3 );
		stadiazionePhenomena.add( phen4 );
		stadiazionePhenomena.add( phenNC );
		
		TypeLink typeLink1 = TypeLinkFactory.createLink();
		TypeLink typeLink2 = TypeLinkFactory.createLink();
		TypeLink typeLink3 = TypeLinkFactory.createLink();
		TypeLink typeLink4 = TypeLinkFactory.createLink();
		
		spessore = FactFactory.createQuantitative();
		spessore.setQuantity( new Quantity() );
		ulcerazione = FactFactory.createQualitative();
		mitosi = FactFactory.createQuantitative();
		mitosi.setQuantity( new Quantity() );
		stadiazione = FactFactory.createQualitative();
		
		root = FactFactory.createComposite();
		FactInsertLink link1 = (FactInsertLink)linkFactory.insertLink( (CompositeFactImpl)root, (QuantitativeFactImpl)spessore );
		link1.setType( typeLink1 );
		FactInsertLink link2 = (FactInsertLink)linkFactory.insertLink( (CompositeFactImpl)root, (QualitativeFactImpl)ulcerazione );
		link2.setType( typeLink2 );
		FactInsertLink link3 = (FactInsertLink)linkFactory.insertLink( (CompositeFactImpl)root, (QuantitativeFactImpl)mitosi );
		link3.setType( typeLink3 );
		FactInsertLink link4 = (FactInsertLink)linkFactory.insertLink( (CompositeFactImpl)root, (QualitativeFactImpl)stadiazione );
		link4.setType( typeLink4 );
		
		viewerRoot = ViewerFactory.createGrid();
		
		ViewerLink viewerLink1 = ViewerLinkFactory.createSubViewer();
		viewerLink1.assignSource(viewerRoot);
		viewerLink1.setPriority( 0l );
		viewerLink1.setSelector( TypeSelectorFactory.createSelector() );
		viewerLink1.getSelector().setTypeLink( typeLink1 );
		
		ViewerLink viewerLink2 = ViewerLinkFactory.createSubViewer();
		viewerLink2.assignSource(viewerRoot);
		viewerLink2.setPriority( 1l );
		viewerLink2.setSelector( TypeSelectorFactory.createSelector() );
		viewerLink2.getSelector().setTypeLink( typeLink2 );
		
		ViewerLink viewerLink3 = ViewerLinkFactory.createSubViewer();
		viewerLink3.assignSource(viewerRoot);
		viewerLink3.setPriority( 2l );
		viewerLink3.setSelector( TypeSelectorFactory.createSelector() );
		viewerLink3.getSelector().setTypeLink( typeLink3 );
		
		ViewerLink viewerLink4 = ViewerLinkFactory.createSubViewer();
		viewerLink4.assignSource(viewerRoot);
		viewerLink4.setPriority( 3l );
		viewerLink4.setSelector( TypeSelectorFactory.createSelector() );
		viewerLink4.getSelector().setTypeLink( typeLink4 );
	}
	
}
