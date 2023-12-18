package it.unifi.ing.stlab.view.model.widgets.output;

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
@DiscriminatorValue("OUTPUT_LIST")
public class OutputList extends ViewerOutput {

	private PanelOrientation orientation;
	
	public OutputList(String uuid) {
		super(uuid);
	}
	protected OutputList() {
		super();
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
		return "../component/outputList.xhtml";
	}
	
	@Override
	public boolean isValidSubViewer(ViewerLink v) {
		if( v == null || !ClassHelper.instanceOf( v, SubViewer.class ))
			return false;
		
		if(listChildren().size() > 0)
			throw new RuntimeException("non è possibile aggiungere più di una sottovista ad un outputList");
		
		if( v.getTarget() != null && ClassHelper.instanceOf( v.getTarget(), ViewerInput.class ))
			throw new RuntimeException("nella outputList non posso utilizzare viste di input");
		
		return true;
	}
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitOutputList(this);
	}

}