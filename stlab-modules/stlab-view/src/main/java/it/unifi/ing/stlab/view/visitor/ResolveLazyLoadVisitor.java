package it.unifi.ing.stlab.view.visitor;

import java.util.List;

import it.unifi.ing.stlab.reflection.model.types.Phenomenon;
import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerContainer;
import it.unifi.ing.stlab.view.model.widgets.ViewerCustom;
import it.unifi.ing.stlab.view.model.widgets.container.Box;
import it.unifi.ing.stlab.view.model.widgets.container.ConditionalPanel;
import it.unifi.ing.stlab.view.model.widgets.container.FactPanel;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.container.Paragraph;
import it.unifi.ing.stlab.view.model.widgets.container.Report;
import it.unifi.ing.stlab.view.model.widgets.container.TabbedPanel;
import it.unifi.ing.stlab.view.model.widgets.input.Combo;
import it.unifi.ing.stlab.view.model.widgets.input.FileUpload;
import it.unifi.ing.stlab.view.model.widgets.input.InputList;
import it.unifi.ing.stlab.view.model.widgets.input.InputTemporal;
import it.unifi.ing.stlab.view.model.widgets.input.InputText;
import it.unifi.ing.stlab.view.model.widgets.input.Suggestion;
import it.unifi.ing.stlab.view.model.widgets.input.TextArea;
import it.unifi.ing.stlab.view.model.widgets.output.Label;
import it.unifi.ing.stlab.view.model.widgets.output.OutputField;
import it.unifi.ing.stlab.view.model.widgets.output.OutputImage;
import it.unifi.ing.stlab.view.model.widgets.output.OutputLink;
import it.unifi.ing.stlab.view.model.widgets.output.OutputList;
import it.unifi.ing.stlab.view.model.widgets.output.OutputMeasurementUnit;
import it.unifi.ing.stlab.view.model.widgets.output.OutputPath;
import it.unifi.ing.stlab.view.model.widgets.output.OutputType;
import it.unifi.ing.stlab.view.model.widgets.output.OutputValue;

public class ResolveLazyLoadVisitor implements ViewerVisitor {

	@Override
	public void visitInputText(InputText it) {
	}
	
	@Override
	public void visitInputTemporal(InputTemporal it) {
	}

	@Override
	public void visitTextArea(TextArea ta) {
	}

	@Override
	public void visitCombo(Combo c) {
	}

	@Override
	public void visitSuggestion(Suggestion s) {
	}

	@Override
	public void visitInputList(InputList i) {
		i.getViewer().accept(this);
	}
	
	@Override
	public void visitFileUpload(FileUpload fu) {
	}

	@Override
	public void visitLabel(Label l) {
	}

	@Override
	public void visitOutputType(OutputType ot) {
	}

	@Override
	public void visitOutputValue(OutputValue ov) {
	}

	@Override
	public void visitOutputMeasurementUnit(OutputMeasurementUnit omu) {
	}

	@Override
	public void visitOutputPath(OutputPath op) {
	}

	@Override
	public void visitOutputList(OutputList ol) {
		ol.getViewer().accept(this);
	}

	@Override
	public void visitOutputField(OutputField of) {
	}

	@Override
	public void visitOutputImage(OutputImage oi) {
	}
	
	@Override
	public void visitOutputLink(OutputLink ol) {
	}

	@Override
	public void visitGrid(Grid g) {
		visitContainer(g);
	}

	@Override
	public void visitFactPanel(FactPanel fp) {
		visitContainer(fp);
	}
	
	@Override
	public void visitBox(Box b) {
		visitContainer(b);
	}
	
	@Override
	public void visitParagraph( Paragraph p ) {
		visitContainer(p);		
	}

	@Override
	public void visitConditionalPanel(ConditionalPanel cp) {
		visitContainer(cp);
		
		for(TypeSelector s : cp.getSelectors()){
			TypeSelector selector = s;
			while ( selector != null ) {
				selector.getRoot();
				selector.getTypeLink();
				selector = selector.getNext();
			}
		}
		
		for(Phenomenon p : cp.getPhenomena())
			p.getName();
		
		if (cp.getClearSelector() != null) {
			TypeSelector clearSelector = cp.getClearSelector();
			while ( clearSelector != null ) {
				clearSelector.getRoot();
				clearSelector.getTypeLink();
				clearSelector = clearSelector.getNext();
			}
		} else if( !cp.getClearSelectors().isEmpty() ){
			List<TypeSelector> clearSelectors = cp.getClearSelectors();
			for( TypeSelector clearSelector : clearSelectors ) {
				while ( clearSelector != null ) {
					clearSelector.getRoot();
					clearSelector.getTypeLink();
					clearSelector = clearSelector.getNext();
				}
			}
		}
	}

	@Override
	public void visitTabbedPanel(TabbedPanel tp) {
		visitContainer(tp);
	}

	@Override
	public void visitReport(Report r) {
		visitContainer(r);
	}
	
	@Override
	public void visitViewerCustom(ViewerCustom vc) {
	}
	
	private void visitContainer(ViewerContainer c){
		for(ViewerLink child : c.listChildren()){
			child.getSource();
			if ( child.getTarget() != null ) {
				child.getTarget().accept(this);
			}
		}
	}
}
