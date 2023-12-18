package it.unifi.ing.stlab.view.model.widgets.input;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.view.model.Viewer;
import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerInput;
import it.unifi.ing.stlab.view.model.widgets.ViewerOutput;
import it.unifi.ing.stlab.view.model.widgets.container.PanelOrientation;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("INPUTLIST")
public class InputList extends ViewerInput {

	private PanelOrientation orientation;
	
	public InputList(String uuid) {
		super(uuid);
		super.setRequired( Boolean.FALSE );
	}
	protected InputList() {
		super();
		super.setRequired( Boolean.FALSE );
	}
	
	@Override
	public void setRequired(Boolean required) {
		if(required == false)
			return;
		
		throw new UnsupportedOperationException();
	}
	
	public PanelOrientation getOrientation() {
		return orientation;
	}
	public void setOrientation(PanelOrientation orientation) {
		this.orientation = orientation;
	}
	
	@Transient
	public Viewer getViewer() {
		if(listChildren().isEmpty())
			return null;
		
		return getByPriority(0l).getTarget();
	}
	
	@Transient
	@Override
	public String getXhtml() {
		return "../component/inputList.xhtml";
	}
	
	@Override
	public boolean isValidSubViewer(ViewerLink sv) {
		if( sv == null || !ClassHelper.instanceOf( sv, SubViewer.class ))
			return false;
		
		if(listChildren().size() > 0)
			throw new RuntimeException("non è possibile aggiungere più di una sottovista ad un inputList");
		
		if ( sv.getTarget() != null && ClassHelper.instanceOf( sv.getTarget(), ViewerOutput.class ))
			throw new RuntimeException("nella inputList non posso utilizzare viste di output");
		
		return true;
	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitInputList(this);
	}
}