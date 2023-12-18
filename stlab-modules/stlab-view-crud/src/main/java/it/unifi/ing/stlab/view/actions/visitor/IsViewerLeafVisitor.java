package it.unifi.ing.stlab.view.actions.visitor;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
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

public class IsViewerLeafVisitor implements ViewerVisitor {
	
	private Boolean leaf;

	@Override
	public void visitInputText(InputText it) {
		leaf = true;

	}

	@Override
	public void visitInputTemporal(InputTemporal it) {
		leaf = true;

	}

	@Override
	public void visitTextArea(TextArea ta) {
		leaf = true;

	}

	@Override
	public void visitCombo(Combo c) {
		leaf = true;

	}

	@Override
	public void visitSuggestion(Suggestion s) {
		leaf = true;

	}

	@Override
	public void visitInputList(InputList i) {
		leaf = true;

	}
	
	@Override
	public void visitFileUpload(FileUpload fu) {
		leaf = true;
	}

	@Override
	public void visitLabel(Label l) {
		leaf = true;

	}

	@Override
	public void visitOutputType(OutputType ot) {
		leaf = true;

	}

	@Override
	public void visitOutputValue(OutputValue ov) {
		leaf = true;

	}

	@Override
	public void visitOutputMeasurementUnit(OutputMeasurementUnit omu) {
		leaf = true;

	}

	@Override
	public void visitOutputPath(OutputPath op) {
		leaf = true;

	}

	@Override
	public void visitOutputList(OutputList ol) {
		leaf = true;

	}

	@Override
	public void visitOutputField(OutputField of) {
		leaf = true;

	}

	@Override
	public void visitOutputImage(OutputImage oi) {
		leaf = true;

	}
	
	@Override
	public void visitOutputLink(OutputLink ol) {
		leaf = true;
		
	}

	@Override
	public void visitGrid(Grid g) {
		leaf = false;

	}

	@Override
	public void visitFactPanel(FactPanel fp) {
		leaf = false;

	}

	@Override
	public void visitBox(Box b) {
		leaf = false;

	}

	@Override
	public void visitConditionalPanel(ConditionalPanel cp) {
		leaf = false;

	}

	@Override
	public void visitTabbedPanel(TabbedPanel tp) {
		leaf = false;

	}

	@Override
	public void visitReport(Report r) {
		leaf = false;

	}

	@Override
	public void visitViewerCustom(ViewerCustom vc) {
		leaf = false;

	}

	public Boolean isLeaf() {
		return leaf;

	}

	@Override
	public void visitParagraph( Paragraph p ) {
		leaf = false;
	}
}
