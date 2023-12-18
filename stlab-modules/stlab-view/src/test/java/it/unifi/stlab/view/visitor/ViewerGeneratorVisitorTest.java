package it.unifi.stlab.view.visitor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.container.Box;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.container.PanelOrientation;
import it.unifi.ing.stlab.view.model.widgets.output.Label;
import it.unifi.ing.stlab.view.model.widgets.output.OutputList;
import it.unifi.ing.stlab.view.model.widgets.output.OutputMeasurementUnit;
import it.unifi.ing.stlab.view.model.widgets.output.OutputPath;
import it.unifi.ing.stlab.view.model.widgets.output.OutputValue;
import it.unifi.ing.stlab.view.visitor.OutputViewerGeneratorVisitor;

import org.junit.Before;
import org.junit.Test;

public class ViewerGeneratorVisitorTest {

	protected OutputViewerGeneratorVisitor visitor;
	protected Type root;
	protected TypeLink link1;
	protected TypeLink link2;
	
	@Before
	public void setUp() {
		root = TypeFactory.createCompositeType();
		root.setName("root type");
		Type child1 = TypeFactory.createTextualType();
		Type child2 = TypeFactory.createQuantitativeType();
		
		link1 = TypeLinkFactory.createLink();
		link1.setName("type link1");
		link1.setPriority(0l);
		link1.setMin(1);
		link1.setMax(3);
		
		link2 = TypeLinkFactory.createLink();
		link2.setName("type link2");
		link2.setPriority(1l);
		link2.setMin(1);
		link2.setMax(1);
		
		link1.assignSource(root);
		link1.assignTarget(child1);
		
		link2.assignSource(root);
		link2.assignTarget(child2);
	}

	@Test
	public void testVisit() {
		visitor = new OutputViewerGeneratorVisitor();
		root.accept(visitor);
		Viewer result = visitor.getResult();
		
		// viewer root = box
		assertNotNull( result );
		assertEquals( Box.class, result.getClass() );
		assertEquals( root, result.getType() );
		assertEquals( 2, result.listChildren().size() );
		
		// viewer child1 = box label (senza selector)
		ViewerLink rootChild1 = result.listChildrenOrdered().get( 0 );
		assertNotNull( rootChild1 );
		assertNull( rootChild1.getSelector() );
		assertNotNull( rootChild1.getTarget() );
		assertNull( rootChild1.getTarget().getType() );
		assertEquals( Label.class, rootChild1.getTarget().getClass() );
		assertEquals( "root type", ((Label)rootChild1.getTarget()).getValue() );
		
		// viewer child2 = box content (grid, senza selector)
		ViewerLink rootChild2 = result.listChildrenOrdered().get( 1 );
		assertNotNull( rootChild2 );
		assertNull( rootChild2.getSelector() );
		assertNotNull( rootChild2.getTarget() );
		assertNull( rootChild2.getTarget().getType() );
		assertEquals( Grid.class, rootChild2.getTarget().getClass() );
		assertEquals( PanelOrientation.vertical, ((Grid)rootChild2.getTarget()).getOrientation() );
		assertEquals( 2, rootChild2.getTarget().listChildren().size() );
		assertTrue( ((Grid)rootChild2.getTarget()).getCollapse() );
		
		// grid child1 = grid spaced horizontal
		ViewerLink gridChild1 = rootChild2.getTarget().listChildrenOrdered().get( 0 );
		assertNotNull( gridChild1 );
		assertNull( gridChild1.getSelector() );
		assertNotNull( gridChild1.getTarget() );
		assertNull( gridChild1.getTarget().getType() );
		assertEquals( Grid.class, gridChild1.getTarget().getClass() );
		assertEquals( PanelOrientation.spaced_horizontal, ((Grid)gridChild1.getTarget()).getOrientation() );
		assertEquals( 2, gridChild1.getTarget().listChildren().size() );
		assertTrue( ((Grid)gridChild1.getTarget()).getCollapse() );
		
		// grid child2 = grid spaced horizontal
		ViewerLink gridChild2 = rootChild2.getTarget().listChildrenOrdered().get( 1 );
		assertNotNull( gridChild2 );
		assertNull( gridChild2.getSelector() );
		assertNotNull( gridChild2.getTarget() );
		assertNull( gridChild2.getTarget().getType() );
		assertEquals( Grid.class, gridChild2.getTarget().getClass() );
		assertEquals( PanelOrientation.spaced_horizontal, ((Grid)gridChild2.getTarget()).getOrientation() );
		assertEquals( 2, gridChild2.getTarget().listChildren().size() );
		assertTrue( ((Grid)gridChild2.getTarget()).getCollapse() );
		
		// leaves 1,2 = outputPath & outputList di outputValue
		ViewerLink leafLink1 = gridChild1.getTarget().listChildrenOrdered().get( 0 );
		assertNotNull( leafLink1 );
		assertNotNull( leafLink1.getSelector() );
		assertEquals( link1, leafLink1.getSelector().getTypeLink() );
		assertNull( leafLink1.getSelector().getNext() );
		assertNotNull( leafLink1.getTarget() );
		assertEquals( OutputPath.class, leafLink1.getTarget().getClass() );
		assertNull( leafLink1.getTarget().getType() );
		
		ViewerLink leafLink2 = gridChild1.getTarget().listChildrenOrdered().get( 1 );
		assertNotNull( leafLink2 );
		assertNotNull( leafLink2.getSelector() );
		assertEquals( link1, leafLink2.getSelector().getTypeLink() );
		assertNull( leafLink2.getSelector().getNext() );
		assertNotNull( leafLink2.getTarget() );
		assertEquals( OutputList.class, leafLink2.getTarget().getClass() );
		assertNull( leafLink2.getTarget().getType() );
		assertEquals( OutputValue.class, ((OutputList)leafLink2.getTarget()).getViewer().getClass() );
		assertEquals( PanelOrientation.vertical, ((OutputList)leafLink2.getTarget()).getOrientation() );
		
		// leaves 3,4 = outputPath & grid con (outputValue, outputMeasurementUnit)
		ViewerLink leafLink3 = gridChild2.getTarget().listChildrenOrdered().get( 0 );
		assertNotNull( leafLink3 );
		assertNotNull( leafLink3.getSelector() );
		assertEquals( link2, leafLink3.getSelector().getTypeLink() );
		assertNull( leafLink3.getSelector().getNext() );
		assertNotNull( leafLink3.getTarget() );
		assertEquals( OutputPath.class, leafLink1.getTarget().getClass() );
		assertNull( leafLink3.getTarget().getType() );
		
		ViewerLink leafLink4 = gridChild2.getTarget().listChildrenOrdered().get( 1 );
		assertNotNull( leafLink4 );
		assertNotNull( leafLink4.getSelector() );
		assertEquals( link2, leafLink4.getSelector().getTypeLink() );
		assertNull( leafLink4.getSelector().getNext() );
		assertNotNull( leafLink4.getTarget() );
		assertEquals( Grid.class, leafLink4.getTarget().getClass() );
		assertNull( leafLink4.getTarget().getType() );
		
		
		ViewerLink subLeafLink1 = leafLink4.getTarget().listChildrenOrdered().get( 0 );
		assertNotNull( subLeafLink1 );
		assertNull( subLeafLink1.getSelector() );
		assertNotNull( subLeafLink1.getTarget() );
		assertEquals( OutputValue.class, subLeafLink1.getTarget().getClass() );
		assertNull( subLeafLink1.getTarget().getType() );
		
		ViewerLink subLeafLink2 = leafLink4.getTarget().listChildrenOrdered().get( 1 );
		assertNotNull( subLeafLink2 );
		assertNull( subLeafLink2.getSelector() );
		assertNotNull( subLeafLink2.getTarget() );
		assertEquals( OutputMeasurementUnit.class, subLeafLink2.getTarget().getClass() );
		assertNull( subLeafLink2.getTarget().getType() );
	}
}
