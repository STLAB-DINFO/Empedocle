package it.unifi.ing.stlab.view.model;

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

public interface ViewerVisitor {

	void visitInputText(InputText it);
	void visitInputTemporal(InputTemporal it);
	void visitTextArea(TextArea ta);
	void visitCombo(Combo c);
	void visitSuggestion(Suggestion s);
	void visitInputList(InputList i);
	void visitFileUpload(FileUpload fu);
	 
	void visitLabel(Label l);
	void visitOutputType(OutputType ot);
	void visitOutputValue(OutputValue ov);
	void visitOutputMeasurementUnit(OutputMeasurementUnit omu);
	void visitOutputPath(OutputPath op);
	void visitOutputList(OutputList ol);
	void visitOutputField(OutputField of);
	void visitOutputImage(OutputImage oi);
	void visitOutputLink(OutputLink ol);
	
	void visitGrid(Grid g);
	void visitFactPanel(FactPanel fp);
	void visitBox(Box b);
	void visitConditionalPanel(ConditionalPanel cp);
	void visitTabbedPanel(TabbedPanel tp);
	void visitReport(Report r);
	void visitParagraph(Paragraph p);
	
	void visitViewerCustom(ViewerCustom vc);
}
