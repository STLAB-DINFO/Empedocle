package it.unifi.ing.stlab.empedocle.actions.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import it.unifi.ing.stlab.empedocle.actions.health.examination.types.ExaminationTypeCloner;
import it.unifi.ing.stlab.empedocle.factory.health.AuthorizationFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationTypeFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ViewerUseFactory;
import it.unifi.ing.stlab.empedocle.model.health.Authorization;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationOperation;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationType;
import it.unifi.ing.stlab.empedocle.model.health.ExaminationTypeContext;
import it.unifi.ing.stlab.empedocle.model.health.ViewerUse;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.users.factory.QualificationFactory;
import it.unifi.ing.stlab.view.factory.ViewerFactory;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ExaminationTypeClonerTest {

	protected ExaminationTypeCloner cloner;
	protected ExaminationType original;
	
	@Before
	public void setUp() {
		// properties
		original = ExaminationTypeFactory.createExaminationType();
		original.setName( "original" );
		original.setDescription( "description" );
		original.setTimeToLive( 100 );
		
		// type
		original.setType( TypeFactory.createTextualType() );
		
		// authorizations
		Authorization auth1 = AuthorizationFactory.createAuthorization();
		auth1.setExamOperation( ExaminationOperation.END_EXAMINATION );
		auth1.setQualification( QualificationFactory.createQualification() );
		Authorization auth2 = AuthorizationFactory.createAuthorization();
		auth2.setExamOperation( ExaminationOperation.END_EXAMINATION );
		auth2.setQualification( QualificationFactory.createQualification() );
		original.addAuthorization( auth1 );
		original.addAuthorization( auth2 );
		
		// viewers
		ViewerUse viewer1 = ViewerUseFactory.createViewerUse();
		viewer1.setContext( ExaminationTypeContext.EDIT );
		viewer1.setQualification( QualificationFactory.createQualification() );
		viewer1.setViewer( ViewerFactory.createInputText() );
		ViewerUse viewer2 = ViewerUseFactory.createViewerUse();
		viewer2.setContext( ExaminationTypeContext.VIEW );
		viewer2.setQualification( QualificationFactory.createQualification() );
		viewer2.setViewer( ViewerFactory.createCombo() );
		ViewerUse viewer3 = ViewerUseFactory.createViewerUse();
		viewer3.setContext( ExaminationTypeContext.REPORT );
		viewer3.setQualification( QualificationFactory.createQualification() );
		viewer3.setViewer( ViewerFactory.createGrid() );
		original.addViewerUse( viewer1 );
		original.addViewerUse( viewer2 );
		original.addViewerUse( viewer3 );
	}
	
	@Test
	public void testClone() {
		cloner = new ExaminationTypeCloner( original );
		
		ExaminationType result = cloner.clone();
		String expectedName = original.getName() + " (copia)";
		
		assertNotNull( result );
		assertEquals( expectedName, result.getName() );
		assertEquals( original.getDescription(), result.getDescription() );
		assertEquals( original.getTimeToLive(), result.getTimeToLive() );
		
		assertEquals( original.getType(), result.getType() );
		
		assertEquals( 2, result.getAuthorizations().size() );
		assertTrue( checkAuthorization( original.getAuthorizations(), result.getAuthorizations() ) );
		
		assertEquals( 3, result.getViewers().size() );
		assertTrue( checkViewerUses( original.getViewers(), result.getViewers() ) );
	}
	
	@Test( expected=RuntimeException.class )
	public void testCloneNull() {
		cloner = new ExaminationTypeCloner( null );
		cloner.clone();
	}
	
	//
	// Utility Methods
	//
	
	private boolean checkAuthorization(Set<Authorization> originalSet, Set<Authorization> copiedSet) {
		for( Authorization auth : originalSet ) {
			if( !hasBeenCloned( auth, copiedSet ) )
				return false;
		}
		
		return true;
	}
	
	private boolean checkViewerUses(Set<ViewerUse> originalSet, Set<ViewerUse> copiedSet) {
		for( ViewerUse viewer : originalSet ) {
			if( !hasBeenCloned( viewer, copiedSet ) )
				return false;
		}
		
		return true;
	}
	
	private boolean hasBeenCloned(Authorization auth, Set<Authorization> copiedSet) {
		for( Authorization copy : copiedSet ) {
			if( auth.getQualification().equals( copy.getQualification() ) &&
					auth.getExamOperation().equals( copy.getExamOperation() ) )
				return true;
		}
		
		return false;
	}
	
	private boolean hasBeenCloned(ViewerUse viewer, Set<ViewerUse> copiedSet) {
		for( ViewerUse copy : copiedSet ) {
			if( viewer.getContext().equals( copy.getContext() ) &&
					viewer.getQualification().equals( copy.getQualification() ) &&
					viewer.getViewer().equals( copy.getViewer() ) )
				return true;
		}
		
		return false;
	}
	
}
