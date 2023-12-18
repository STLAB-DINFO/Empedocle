package it.unifi.ing.stlab.view.actions;

import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.view.actions.wrappers.ViewerBean;
import it.unifi.ing.stlab.view.factory.ViewerFactory;

import org.junit.Before;
import org.junit.Test;

public class ViewerBeanTest {

	@Before
	public void setUp() {
	}

	@Test( expected = IllegalArgumentException.class )
	public void testConstructorNoType() {
		new ViewerBean( null );
	}
	
	@Test
	public void testComposite() {
		ViewerBean viewerBean = new ViewerBean( ViewerFactory.createGrid() );
		assertNotNull( viewerBean.getTreeRoot() );
	}
	
}

