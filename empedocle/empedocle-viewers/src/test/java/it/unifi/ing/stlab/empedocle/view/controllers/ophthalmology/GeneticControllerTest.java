package it.unifi.ing.stlab.empedocle.view.controllers.ophthalmology;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.reflection.factory.types.PhenomenonFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.QualitativeFact;
import it.unifi.ing.stlab.reflection.model.facts.TemporalFact;
import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.view.factory.ViewerFactory;
import it.unifi.ing.stlab.view.factory.ViewerLinkFactory;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.links.ViewerLink;

import org.junit.Before;
import org.junit.Test;

public class GeneticControllerTest {

	private GeneticController controller;
	
	private Viewer viewer;
	private ViewerLink viewerLink;
	private TypeSelector selector;
	
	private Fact root;
	private Phenomenon phenomenon;
	
	@Before
	public void setUp() {
		controller = new GeneticController();
		
		viewer = ViewerFactory.createGrid();
		viewerLink = ViewerLinkFactory.createSubViewer();
		selector = mock(TypeSelector.class);
		viewerLink.assignSource(viewer);
		viewerLink.setSelector(selector);
		
		root = FactFactory.createComposite();
		phenomenon = PhenomenonFactory.createPhenomenon();
		phenomenon.setName("Si");
	}
	
	@Test
	public void testIsInformativeDateRendered() {
		QualitativeFact informative = FactFactory.createQualitative();
		FieldUtils.assignField(controller, "informative", informative);
		TemporalFact informativeDate = FactFactory.createTemporal();
		viewerLink.setPriority(1l);
		when(selector.apply(root)).thenReturn(informativeDate);
		
		Boolean result = controller.isInformativeDateRendered(root, viewer);
		assertFalse(result);
		assertNull(informativeDate.getDate());
		
		informative.setPhenomenon(phenomenon);
		result = controller.isInformativeDateRendered(root, viewer);
		assertTrue(result);
		assertNotNull(informativeDate.getDate());
		
		informative.setPhenomenon(null);
		result = controller.isInformativeDateRendered(root, viewer);
		assertFalse(result);
		assertNull(informativeDate.getDate());
	}
	
	@Test
	public void testIsAdviceDateRendered() {
		QualitativeFact advice = FactFactory.createQualitative();
		FieldUtils.assignField(controller, "advice", advice);
		TemporalFact adviceDate = FactFactory.createTemporal();
		viewerLink.setPriority(3l);
		when(selector.apply(root)).thenReturn(adviceDate);
		
		Boolean result = controller.isAdviceDateRendered(root, viewer);
		assertFalse(result);
		assertNull(adviceDate.getDate());
		
		advice.setPhenomenon(phenomenon);
		result = controller.isAdviceDateRendered(root, viewer);
		assertTrue(result);
		assertNotNull(adviceDate.getDate());
		
		advice.setPhenomenon(null);
		result = controller.isAdviceDateRendered(root, viewer);
		assertFalse(result);
		assertNull(adviceDate.getDate());
	}
	
	@Test
	public void testIsParentVisitDateRendered() {
		QualitativeFact parentVisit = FactFactory.createQualitative();
		FieldUtils.assignField(controller, "parentVisit", parentVisit);
		TemporalFact parentVisitDate = FactFactory.createTemporal();
		viewerLink.setPriority(5l);
		when(selector.apply(root)).thenReturn(parentVisitDate);
		
		Boolean result = controller.isParentVisitDateRendered(root, viewer);
		assertFalse(result);
		assertNull(parentVisitDate.getDate());
		
		parentVisit.setPhenomenon(phenomenon);
		result = controller.isParentVisitDateRendered(root, viewer);
		assertTrue(result);
		assertNotNull(parentVisitDate.getDate());
		
		parentVisit.setPhenomenon(null);
		result = controller.isParentVisitDateRendered(root, viewer);
		assertFalse(result);
		assertNull(parentVisitDate.getDate());
	}
	
	@Test
	public void testIsFamilyAdviceDateRendered() {
		QualitativeFact familyAdvice = FactFactory.createQualitative();
		FieldUtils.assignField(controller, "familyAdvice", familyAdvice);
		TemporalFact familyAdviceDate = FactFactory.createTemporal();
		viewerLink.setPriority(7l);
		when(selector.apply(root)).thenReturn(familyAdviceDate);
		
		Boolean result = controller.isFamilyAdviceDateRendered(root, viewer);
		assertFalse(result);
		assertNull(familyAdviceDate.getDate());
		
		familyAdvice.setPhenomenon(phenomenon);
		result = controller.isFamilyAdviceDateRendered(root, viewer);
		assertTrue(result);
		assertNotNull(familyAdviceDate.getDate());
		
		familyAdvice.setPhenomenon(null);
		result = controller.isFamilyAdviceDateRendered(root, viewer);
		assertFalse(result);
		assertNull(familyAdviceDate.getDate());
	}
	
	@Test
	public void testIsRegisterDateRendered() {
		QualitativeFact register = FactFactory.createQualitative();
		FieldUtils.assignField(controller, "register", register);
		TemporalFact registerDate = FactFactory.createTemporal();
		viewerLink.setPriority(9l);
		when(selector.apply(root)).thenReturn(registerDate);
		
		Boolean result = controller.isRegisterDateRendered(root, viewer);
		assertFalse(result);
		assertNull(registerDate.getDate());
		
		register.setPhenomenon(phenomenon);
		result = controller.isRegisterDateRendered(root, viewer);
		assertTrue(result);
		assertNotNull(registerDate.getDate());
		
		register.setPhenomenon(null);
		result = controller.isRegisterDateRendered(root, viewer);
		assertFalse(result);
		assertNull(registerDate.getDate());
	}
	
	@Test
	public void testIsSecondVersion() {
		assertFalse( controller.isSecondVersion(root, viewer) );
		
		FieldUtils.assignField(controller, "secondVersion", null);
		viewerLink.setPriority(8l);
		
		assertTrue( controller.isSecondVersion(root, viewer) );
	}
}
