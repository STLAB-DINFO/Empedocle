package it.unifi.ing.stlab.view.dsl;

import java.util.Collection;

import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.links.Tab;
import it.unifi.ing.stlab.view.model.links.TypeSelector;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerCustom;
import it.unifi.ing.stlab.view.model.widgets.container.Box;
import it.unifi.ing.stlab.view.model.widgets.container.ConditionalPanel;
import it.unifi.ing.stlab.view.model.widgets.container.FactPanel;
import it.unifi.ing.stlab.view.model.widgets.container.Grid;
import it.unifi.ing.stlab.view.model.widgets.container.PanelOrientation;
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

public class ViewerEncoderVisitor implements ViewerVisitor {

	private StringBuffer definition;
	private int depth;
	
	public ViewerEncoderVisitor(){
		definition = new StringBuffer();
		this.depth = 0;
	}
	
	@Override
	public void visitInputText(InputText it) {
		definition.append("inputText");
		
		if(it.getRequired() != null && it.getRequired())
			definition.append(" required");
		
		if(it.getInputLength() != null){
			definition.append(" length=");
			definition.append(it.getInputLength());
			
		}
		
	}
	
	@Override
	public void visitInputTemporal(InputTemporal it) {
		definition.append("inputTemporal");
		
		if(it.getRequired() != null && it.getRequired())
			definition.append(" required");
		
	}

	@Override
	public void visitTextArea(TextArea ta) {
		definition.append("textArea");
		
		if(ta.getRequired() != null && ta.getRequired())
			definition.append(" required");
		
		if(ta.getInputLength() != null){
			definition.append(" length=");
			definition.append(ta.getInputLength());
			
		}
		
	}

	@Override
	public void visitCombo(Combo c) {
		definition.append("combo");
		
		if(c.getRequired() != null && c.getRequired())
			definition.append(" required");
		
	}

	@Override
	public void visitSuggestion(Suggestion s) {
		definition.append("suggestion");
		
		if(s.getRequired() != null && s.getRequired())
			definition.append(" required");

	}

	@Override
	public void visitInputList(InputList i) {
		definition.append("inputList");
		if(i.getOrientation().equals(PanelOrientation.horizontal))
			definition.append(" horizontal");
		
		definition.append(" {");
		space();
		
		i.getViewer().accept(this);
		
		space();
		definition.append("}");
		
	}
	
	@Override
	public void visitFileUpload(FileUpload fu) {
		definition.append("fileUpload");
	}


	@Override
	public void visitLabel(Label l) {
		definition.append("label");
		if(l.getValue() == null || l.getValue().isEmpty())
			return;
		
		space();
		definition.append("\"");
		definition.append(l.getValue());
		definition.append("\"");
		
	}

	@Override
	public void visitOutputType(OutputType ot) {
		definition.append("outputType");
		
	}

	@Override
	public void visitOutputValue(OutputValue ov) {
		definition.append("outputValue");
		
	}
	
	@Override
	public void visitOutputLink(OutputLink ol) {
		definition.append("outputLink");
		
	}	

	@Override
	public void visitOutputMeasurementUnit(OutputMeasurementUnit omu) {
		definition.append("outputMeasurementUnit");
		
	}

	@Override
	public void visitOutputPath(OutputPath op) {
		definition.append("outputPath");

	}

	@Override
	public void visitOutputList(OutputList ol) {
		definition.append("outputList");
		if(ol.getOrientation().equals(PanelOrientation.horizontal))
			definition.append(" horizontal");
		
		definition.append(" {");
		space();
		
		ol.getViewer().accept(this);
		
		space();
		definition.append("}");
		
	}

	@Override
	public void visitOutputField(OutputField of) {
		definition.append("out");
		space();
		definition.append(of.getPath());
		
	}

	@Override
	public void visitOutputImage(OutputImage oi) {
		definition.append("outputImage");
		space();
		definition.append("\"");
		definition.append(oi.getImagePath());
		definition.append("\"");
		
	}

	@Override
	public void visitGrid(Grid g) {
		definition.append("grid");
		if(g.getOrientation().equals(PanelOrientation.horizontal))
			definition.append(" horizontal");
		
		if(g.getOrientation().equals(PanelOrientation.spaced_horizontal))
			definition.append(" spaced_horizontal");
		
		if(g.getCollapse())
			definition.append(" collapse");
		
		definition.append(" {");
		endLine();
		
		this.depth++;
		visitViewerLinks(g.listChildrenOrdered());
		this.depth--;
		
		tab();
		definition.append("}");

		endLine();
	}
	
	@Override
	public void visitParagraph( Paragraph p ) {
		definition.append("paragraph");
		
		
		if(p.getCollapse())
			definition.append(" collapse");
		
		definition.append(" {");
		endLine();
		
		this.depth++;
		visitViewerLinks(p.listChildrenOrdered());
		this.depth--;
		
		tab();
		definition.append("}");

		endLine();		
	}

	@Override
	public void visitBox(Box b) {
		definition.append("box");
		
		if(b.getCollapse())
			definition.append(" collapse");
		
		definition.append(" {");
		endLine();
		
		this.depth++;
		visitViewerLinks(b.listChildrenOrdered());
		this.depth--;
		
		tab();
		definition.append("}");
		
		
		endLine();
	}

	@Override
	public void visitConditionalPanel(ConditionalPanel cp) {
		definition.append("conditionalPanel {");
		endLine();
		
		this.depth++;
		tab();
		visitSelector(cp.getSelectors().get(0));
		this.depth--;
		
		tab();
		
		definition.append(": ");
		definition.append("\"");
		definition.append(cp.getPhenomena().get(0).getName());
		definition.append("\"");
		
		for(int i=0; i<cp.getOperators().size(); i++){
			space();
			definition.append(cp.getOperators().get(i));
			space();
			visitSelector(cp.getSelectors().get(i+1));
			definition.append(": ");
			definition.append("\"");
			definition.append(cp.getPhenomena().get(i+1).getName());
			definition.append("\"");
		}
		endLine();
		
		if(cp.isClear()){
			this.depth++;
			tab();
			definition.append("clear ");
			this.depth--;
			if( cp.getClearSelector() != null ) {
				visitSelector(cp.getClearSelector());
			} else {
				for( TypeSelector clearSelectors : cp.getClearSelectors() ) {
					visitSelector(clearSelectors);
				}
			}
				
			endLine();
		}
		
		this.depth++;
		visitViewerLinks(cp.listChildrenOrdered());
		this.depth--;
		
		tab();
		definition.append("}");
		
		endLine();
	}

	@Override
	public void visitTabbedPanel(TabbedPanel tp) {
		definition.append("tabbedPanel {");
		
		endLine();

		for(ViewerLink t : tp.listChildrenOrdered()){
			this.depth++;
			tab();
			this.depth--;
			definition.append("\"");
			definition.append(((Tab)t).getLabel());
			definition.append("\"");
			definition.append(" ; ");
			visitViewerLink(t);
		}
		
		definition.append("}");
		
		endLine();
	}

	@Override
	public void visitReport(Report r) {
		definition.append("report {");
		endLine();
		
		this.depth++;
		visitViewerLinks(r.listChildrenOrdered());
		this.depth--;
		
		tab();
		definition.append("}");
		
		endLine();
	}
	
	@Override
	public void visitFactPanel(FactPanel fq) {
		definition.append( "factPanel query \"" );
		definition.append( fq.getQuery().getName() );
		definition.append( "\" {" );
		endLine();
		
		this.depth++;
		visitViewerLinks(fq.listChildrenOrdered());
		this.depth--;
		
		tab();
		definition.append("}");
		
		endLine();
	}
	
	@Override
	public void visitViewerCustom(ViewerCustom vc) {
		definition.append("view \"");
		definition.append(vc.getName());
		definition.append("\"");
	}
	

	private void visitViewerLinks(Collection<ViewerLink> links){
		for(ViewerLink sv : links)
			visitViewerLink(sv);
	}
	
	private void visitViewerLink(ViewerLink link){
		tab();
		visitSelector(link.getSelector());
		definition.append(": ");
		
		link.getTarget().accept(this);
		
		endLine();
	}
	
	private void visitSelector(TypeSelector sel){
		StringBuffer selectorBuffer = new StringBuffer();
		TypeSelector next = sel;
		
		while(next != null){
			selectorBuffer.append("\"");
			selectorBuffer.append(next.getTypeLink().getName());
			selectorBuffer.append("\"");
		
			next = next.getNext();
			if(next != null)
				selectorBuffer.append(".");
		}
		
		if(selectorBuffer.length() > 0)
			selectorBuffer.append( " " );
		
		definition.append( selectorBuffer.toString() );
	}
	
	private void endLine(){
		definition.append("\n");
	}
	
	private void tab() {
		for(int i = 0; i < depth; i++)
			definition.append("\t");
	}
	
	private void space() {
		definition.append(" ");
	}
	
	public String getDefinition() {
		return definition.toString();
	}
}
