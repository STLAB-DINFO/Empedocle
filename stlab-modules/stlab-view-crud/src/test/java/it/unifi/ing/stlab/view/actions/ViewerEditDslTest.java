package it.unifi.ing.stlab.view.actions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.view.actions.ViewerEditDsl;
import it.unifi.ing.stlab.view.actions.ViewerPreview;
import it.unifi.ing.stlab.view.dao.ViewerDao;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.junit.Before;
import org.junit.Test;


public class ViewerEditDslTest {
	protected FacesContext facesContext;
	protected ViewerPreview viewerPreview;
	protected ViewerEditDsl controller;
	protected ViewerDao viewerDao;
	
	@Before
	public void setUp() {
		facesContext = mock( FacesContext.class );
		
		controller = new ViewerEditDsl();
		FieldUtils.assignField( controller, "facesContext", facesContext );
	}
	
	@Test
	public void testValidate() {
		controller.setDefinition( "inputText" );
		controller.validate();
		
		verify( facesContext, times(1) ).addMessage( (String)isNull(), any( FacesMessage.class ) );
		assertNotNull( controller.getViewer() );
	}

	@Test
	public void testValidateNullDefinition() {
		controller.validate();

		verify( facesContext, never() ).addMessage( (String)isNull(), any( FacesMessage.class ) );
		assertNull( controller.getViewer() );
	}
	
	@Test
	public void testHasPreview() {
		assertFalse( controller.checkPreview() );
		
		controller.setType( TypeFactory.createTextualType() );
		assertFalse( controller.checkPreview() );
		
		controller.setDefinition( "inputText" );
		controller.validate();
		
		assertTrue( controller.checkPreview() );
		
//		controller.setDefinition( "label \"ciao\"" );
//		assertFalse( controller.hasPreview() );
		
	}
	
	@Test
	public void testCanSave(){
		assertFalse( controller.canSave() );
		
		controller.setDefinition( "inputText" );
		controller.validate();
		
		assertFalse( controller.canSave() );
		
		controller.setName( "" );
		
		assertFalse( controller.canSave() );
		
		controller.setName( "name" );
		
		assertTrue( controller.canSave() );
	}
	
}
