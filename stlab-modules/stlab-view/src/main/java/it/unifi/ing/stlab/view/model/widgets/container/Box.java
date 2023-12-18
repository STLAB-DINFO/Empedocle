package it.unifi.ing.stlab.view.model.widgets.container;

import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.view.model.ViewerVisitor;
import it.unifi.ing.stlab.view.model.links.SubViewer;
import it.unifi.ing.stlab.view.model.links.ViewerLink;
import it.unifi.ing.stlab.view.model.widgets.ViewerContainer;
import it.unifi.ing.stlab.view.model.widgets.ViewerOutput;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("BOX")
public class Box extends ViewerContainer {

	public Box(String uuid) {
		super(uuid);
	}
	protected Box() {
		super();
	}
	
	@Override
	public boolean isValidSubViewer(ViewerLink sv) {
		if( sv == null || !ClassHelper.instanceOf( sv, SubViewer.class ))
			return false;
		
		switch (listChildren().size()) {
		case 0:
			return true;
		case 1:
			if (getByPriority(0l).getTarget() == null || !ClassHelper.instanceOf( getByPriority(0l).getTarget(), ViewerOutput.class )) {
				throw new RuntimeException( "Box label not valid" );
			} else {
				return true;
			}
		case 2:
			throw new RuntimeException( "Box cannot have more then 2 children" );
		}
		
		return false;
	}
	
	@Transient
	public int getSize(){
		return getChildren().size();
	}

	@Transient
	@Override
	public String getXhtml() {
		return "../component/box.xhtml";
	}	
	
	@Override
	public void accept(ViewerVisitor v){
		v.visitBox(this);
	}
}