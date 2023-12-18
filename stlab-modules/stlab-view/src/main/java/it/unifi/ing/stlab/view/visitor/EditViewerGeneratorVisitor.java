package it.unifi.ing.stlab.view.visitor;

import java.util.UUID;

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
import it.unifi.ing.stlab.view.model.widgets.input.Combo;
import it.unifi.ing.stlab.view.model.widgets.input.InputList;
import it.unifi.ing.stlab.view.model.widgets.input.InputTemporal;
import it.unifi.ing.stlab.view.model.widgets.input.InputText;
import it.unifi.ing.stlab.view.model.widgets.input.Suggestion;
import it.unifi.ing.stlab.view.model.widgets.output.Label;
import it.unifi.ing.stlab.view.model.widgets.output.OutputPath;


public class EditViewerGeneratorVisitor extends ViewerGeneratorVisitor {

	private TypeLink fatherLink;
	private Viewer result;
	
	public EditViewerGeneratorVisitor(){
	}
	
	public EditViewerGeneratorVisitor(TypeLink fatherLink){
		this.fatherLink = fatherLink;
	}
	
	@Override
	public void visitTextualType(TextualType type) {
		// grid
		Grid grid = ViewerFactory.createGrid();
		grid.setOrientation( PanelOrientation.spaced_horizontal );
		grid.setCollapse( false );
		
		// outputPath
		ViewerLink link1 = ViewerLinkFactory.createSubViewer();
		OutputPath outPath = ViewerFactory.createOutputPath();
		link1.assignSource( grid );
		link1.assignTarget( outPath );
		
		adjustSelector(link1);
		
		// inputText | inputList
		ViewerLink link2 = ViewerLinkFactory.createSubViewer();
		link2.assignSource( grid );
		link2.assignTarget( createInputListAndInputText() );
		
		adjustSelector(link2);
		
		result = grid;
	}

	@Override
	public void visitEnumeratedType(EnumeratedType type) {
		// grid
		Grid grid = ViewerFactory.createGrid();
		grid.setOrientation( PanelOrientation.spaced_horizontal );
		grid.setCollapse( false );
		
		// outputPath
		ViewerLink link1 = ViewerLinkFactory.createSubViewer();
		OutputPath outPath = ViewerFactory.createOutputPath();
		link1.assignSource( grid );
		link1.assignTarget( outPath );
		
		adjustSelector(link1);
		
		// inputText | inputList
		ViewerLink link2 = ViewerLinkFactory.createSubViewer();
		link2.assignSource( grid );
		link2.assignTarget( createInputListAndCombo() );
		
		adjustSelector(link2);
		
		result = grid;
	}

	@Override
	public void visitQueriedType(QueriedType type) {
		// grid
		Grid grid = ViewerFactory.createGrid();
		grid.setOrientation( PanelOrientation.spaced_horizontal );
		grid.setCollapse( false );
		
		// outputPath
		ViewerLink link1 = ViewerLinkFactory.createSubViewer();
		OutputPath outPath = ViewerFactory.createOutputPath();
		link1.assignSource( grid );
		link1.assignTarget( outPath );
		
		adjustSelector(link1);
		
		// inputText | inputList
		ViewerLink link2 = ViewerLinkFactory.createSubViewer();
		link2.assignSource( grid );
		link2.assignTarget( createInputListAndSuggestion() );
		
		adjustSelector(link2);
		
		result = grid;
	}
	
	@Override
	public void visitTemporalType(TemporalType type) {
		// grid
		Grid grid = ViewerFactory.createGrid();
		grid.setOrientation( PanelOrientation.spaced_horizontal );
		grid.setCollapse( false );
		
		// outputPath
		ViewerLink link1 = ViewerLinkFactory.createSubViewer();
		OutputPath outPath = ViewerFactory.createOutputPath();
		link1.assignSource( grid );
		link1.assignTarget( outPath );
		
		adjustSelector(link1);
		
		// inputText | inputList
		ViewerLink link2 = ViewerLinkFactory.createSubViewer();
		link2.assignSource( grid );
		link2.assignTarget( createInputListAndInputTemporal() );
		
		adjustSelector(link2);
		
		result = grid;
	}

	@Override
	public void visitQuantitativeType(QuantitativeType type) {
		// grid
		Grid grid = ViewerFactory.createGrid();
		grid.setOrientation( PanelOrientation.spaced_horizontal );
		grid.setCollapse( false );
		
		// outputPath
		ViewerLink link1 = ViewerLinkFactory.createSubViewer();
		OutputPath outPath = ViewerFactory.createOutputPath();
		link1.assignSource( grid );
		link1.assignTarget( outPath );
		
		adjustSelector(link1);
		
		
		// FIXME qui l'input list va messo sopra!
		// sub grid
		Grid subGrid = ViewerFactory.createGrid();
		subGrid.setOrientation( PanelOrientation.spaced_horizontal );
		subGrid.setCollapse( false );
		ViewerLink link2 = ViewerLinkFactory.createSubViewer();
		link2.assignSource( grid );
		link2.assignTarget( subGrid );
		
		adjustSelector(link2);
		
		// inputText | inputList
		ViewerLink subLink1 = ViewerLinkFactory.createSubViewer();
		subLink1.assignSource( subGrid );
		subLink1.assignTarget( createInputListAndInputText() );
		
		// combo
		ViewerLink subLink2 = ViewerLinkFactory.createSubViewer();
		Combo unit = ViewerFactory.createCombo();
		subLink2.assignSource( subGrid );
		subLink2.assignTarget( unit );
		
		result = grid;
	}

	//FIXME quand'è che deve aggiungere il selector?
	@Override
	public void visitCompositeType(CompositeType type) {
		//box
		Box box = ViewerFactory.createBox();
		box.setCollapse( false );
		
		//label
		Label boxLabel = ViewerFactory.createLabel();
		boxLabel.setValue( getLabelValue( type ) );
		ViewerLink link1 = ViewerLinkFactory.createSubViewer();
		link1.assignSource( box );
		link1.assignTarget( boxLabel );
		
		//content
		Grid boxContent = ViewerFactory.createGrid();
		boxContent.setOrientation( PanelOrientation.vertical );
		boxContent.setCollapse( false );
		ViewerLink link2 = ViewerLinkFactory.createSubViewer();
		link2.assignSource( box );
		link2.assignTarget( boxContent );
		
		adjustSelector( link2 );
		
		for( TypeLink child : type.listChildrenOrdered() ) {
			EditViewerGeneratorVisitor visitor = new EditViewerGeneratorVisitor( child );
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

	private Viewer createInputListAndInputText() {
		if( fatherLink == null || fatherLink.getMax().equals( 1 ) ) 
			return ViewerFactory.createInputText();
		
		InputList inputList = ViewerFactory.createInputList();
		inputList.setOrientation( PanelOrientation.vertical );
		
		InputText inputText = ViewerFactory.createInputText();
		ViewerLink link = ViewerLinkFactory.createSubViewer();
		link.assignSource( inputList );
		link.assignTarget( inputText );
		
		return inputList;
	}
	
	private Viewer createInputListAndSuggestion() {
		if( fatherLink == null || fatherLink.getMax().equals( 1 ) ) 
			return ViewerFactory.createSuggestion();
		
		InputList inputList = ViewerFactory.createInputList();
		inputList.setOrientation( PanelOrientation.vertical );
		
		Suggestion suggestion = ViewerFactory.createSuggestion();
		ViewerLink link = ViewerLinkFactory.createSubViewer();
		link.assignSource( inputList );
		link.assignTarget( suggestion );
		
		return inputList;
	}
	
	
	private Viewer createInputListAndCombo() {
		if( fatherLink == null || fatherLink.getMax().equals( 1 ) ) 
			return ViewerFactory.createCombo();
		
		InputList inputList = ViewerFactory.createInputList();
		inputList.setOrientation( PanelOrientation.vertical );
		
		Combo combo = ViewerFactory.createCombo();
		ViewerLink link = ViewerLinkFactory.createSubViewer();
		link.assignSource( inputList );
		link.assignTarget( combo );
		
		return inputList;
	}
	
	private Viewer createInputListAndInputTemporal() {
		if( fatherLink == null || fatherLink.getMax().equals( 1 ) ) 
			return ViewerFactory.createInputTemporal();
		
		InputList inputList = ViewerFactory.createInputList();
		inputList.setOrientation( PanelOrientation.vertical );
		
		InputTemporal inputTemporal = ViewerFactory.createInputTemporal();
		ViewerLink link = ViewerLinkFactory.createSubViewer();
		link.assignSource( inputList );
		link.assignTarget( inputTemporal );
		
		return inputList;
	}
	
	private String getLabelValue(CompositeType type) {
		return fatherLink == null ? type.getName() : fatherLink.getName();
	}
	

}
