package it.unifi.ing.stlab.view.visitor;

import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.EnumeratedType;
import it.unifi.ing.stlab.reflection.model.types.QuantitativeType;
import it.unifi.ing.stlab.reflection.model.types.QueriedType;
import it.unifi.ing.stlab.reflection.model.types.TemporalType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.view.factory.ViewerFactory;
import it.unifi.ing.stlab.view.factory.ViewerLinkFactory;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.container.Box;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.container.PanelOrientation;
import it.unifi.ing.stlab.view.model.widgets.output.Label;
import it.unifi.ing.stlab.view.model.widgets.output.OutputList;
import it.unifi.ing.stlab.view.model.widgets.output.OutputMeasurementUnit;
import it.unifi.ing.stlab.view.model.widgets.output.OutputPath;
import it.unifi.ing.stlab.view.model.widgets.output.OutputValue;

import java.util.UUID;


public class OutputViewerGeneratorVisitor extends ViewerGeneratorVisitor {

	private TypeLink fatherLink;
	private Viewer result;
	
	public OutputViewerGeneratorVisitor(){
	}
	
	public OutputViewerGeneratorVisitor(TypeLink fatherLink){
		this.fatherLink = fatherLink;
	}
	
	@Override
	public void visitTextualType(TextualType type) {
		baseVisit();
	}

	@Override
	public void visitEnumeratedType(EnumeratedType type) {
		baseVisit();
	}

	@Override
	public void visitQueriedType(QueriedType type) {
		baseVisit();
	}

	@Override
	public void visitQuantitativeType(QuantitativeType type) {
		// grid
		Grid grid = ViewerFactory.createGrid();
		grid.setOrientation( PanelOrientation.spaced_horizontal );
		grid.setCollapse( true );
		
		// outputPath
		ViewerLink link1 = ViewerLinkFactory.createSubViewer();
		OutputPath outPath = ViewerFactory.createOutputPath();
		link1.assignSource( grid );
		link1.assignTarget( outPath );
		
		adjustSelector(link1);
		
		
		// FIXME qui l'output list va messo sopra!
		// sub grid
		Grid subGrid = ViewerFactory.createGrid();
		subGrid.setOrientation( PanelOrientation.spaced_horizontal );
		subGrid.setCollapse( true );
		ViewerLink link2 = ViewerLinkFactory.createSubViewer();
		link2.assignSource( grid );
		link2.assignTarget( subGrid );
		
		adjustSelector(link2);
		
		// outputValue | outputList
		ViewerLink subLink1 = ViewerLinkFactory.createSubViewer();
		subLink1.assignSource( subGrid );
		subLink1.assignTarget( createOutput() );
		
		// outputMeasurementUnit
		ViewerLink subLink2 = ViewerLinkFactory.createSubViewer();
		OutputMeasurementUnit outUnit = ViewerFactory.createOutputUnit();
		subLink2.assignSource( subGrid );
		subLink2.assignTarget( outUnit );
		
		result = grid;
	}

	@Override
	public void visitTemporalType(TemporalType type) {
		baseVisit();
	}

	//FIXME quand'è che deve aggiungere il selector?
	@Override
	public void visitCompositeType(CompositeType type) {
		//box
		Box box = ViewerFactory.createBox();
		box.setCollapse( true );
		
		//label
		Label boxLabel = ViewerFactory.createLabel();
		boxLabel.setValue( getLabelValue( type ) );
		ViewerLink link1 = ViewerLinkFactory.createSubViewer();
		link1.assignSource( box );
		link1.assignTarget( boxLabel );
		
		//content
		Grid boxContent = ViewerFactory.createGrid();
		boxContent.setOrientation( PanelOrientation.vertical );
		boxContent.setCollapse( true );
		ViewerLink link2 = ViewerLinkFactory.createSubViewer();
		link2.assignSource( box );
		link2.assignTarget( boxContent );
		
		adjustSelector( link2 );
		
		for( TypeLink child : type.listChildrenOrdered() ) {
			OutputViewerGeneratorVisitor visitor = new OutputViewerGeneratorVisitor( child );
			child.getTarget().accept( visitor );
			
			ViewerLink link = ViewerLinkFactory.createSubViewer();
			link.assignSource( boxContent );
			link.assignTarget( visitor.getResult() );
		}
		
		if( fatherLink == null )
			box.setType( type );
		
		result = box;

	}

	public Viewer getResult() {
		return this.result;
	}
	
	//
	// Private Methods
	//
	
	private void adjustSelector(ViewerLink link2) {
		if( fatherLink != null ) {
			TypeSelector selector1 = new TypeSelector( UUID.randomUUID().toString() );
			selector1.setTypeLink( fatherLink );
			link2.setSelector( selector1 );
		}
	}

	
	private void baseVisit() {
		// grid
		Grid grid = ViewerFactory.createGrid();
		grid.setOrientation( PanelOrientation.spaced_horizontal );
		grid.setCollapse( true );
		
		// outputPath
		ViewerLink link1 = ViewerLinkFactory.createSubViewer();
		OutputPath outPath = ViewerFactory.createOutputPath();
		link1.assignSource( grid );
		link1.assignTarget( outPath );
		
		adjustSelector(link1);
		
		// outputValue | outputList
		ViewerLink link2 = ViewerLinkFactory.createSubViewer();
		link2.assignSource( grid );
		link2.assignTarget( createOutput() );
		
		adjustSelector(link2);
		
		result = grid;
	}

	private Viewer createOutput() {
		if( fatherLink == null || fatherLink.getMax().equals( 1 ) ) 
			return ViewerFactory.createOutputValue();
		
		OutputList outputList = ViewerFactory.createOutputList();
		outputList.setOrientation( PanelOrientation.vertical );
		OutputValue outputValue = ViewerFactory.createOutputValue();
		ViewerLink link = ViewerLinkFactory.createSubViewer();
		link.assignSource( outputList );
		link.assignTarget( outputValue );
		
		return outputList;
	}
	
	private String getLabelValue(CompositeType type) {
		return fatherLink == null ? type.getName() : fatherLink.getName();
	}
	

}
